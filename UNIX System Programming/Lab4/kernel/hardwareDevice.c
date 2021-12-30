//hardwareDevice.c - A kernel level program for writing to a hardware device
/*
Last Name: Moss
First Name: Tanner
Date Modified: 10/15/2021
*/

#include <linux/cdev.h>     /* char device stuff */
#include <linux/delay.h>     /* msleep */
#include <linux/errno.h>    /* error codes */
#include <linux/fs.h> 	    /* file stuff */
#include <linux/init.h>       /* module_init, module_exit */
#include <linux/kernel.h>   /* printk() */
#include <linux/kthread.h>   /* kthread_create */
#include <linux/module.h>     /* version info, MODULE_LICENSE, MODULE_AUTHOR, printk() */
#include <linux/uaccess.h>
#include "hardwareDevice.h"

MODULE_DESCRIPTION("Hardware Device Linux driver");
MODULE_LICENSE("GPL");

#define BUF_LEN 16

int register_device(void);
void unregister_device(void);
static int hardware_device_open(struct inode *inode, struct file *file);
static int hardware_device_close(struct inode *inode, struct file *file);
static ssize_t hardware_device_read(struct file *filp, char __user *buf, size_t len, loff_t *off);
static long hardware_device_ioctl(struct file *file, unsigned int cmd, unsigned long arg);


/*===============================================================================================*/
static int hardware_device_init(void)
{
    int result = 0;
    printk( KERN_NOTICE "Hardware-Device: Initialization started\n" );

    result = register_device();
    return result;
}

/*===============================================================================================*/
static void hardware_device_exit(void)
{
    printk( KERN_NOTICE "Hardware-Device: Exiting\n" );
    unregister_device();
}

/*===============================================================================================*/
module_init(hardware_device_init);
module_exit(hardware_device_exit);

/*===============================================================================================*/
static struct file_operations simple_driver_fops =
{
	.owner = THIS_MODULE,
	.open = hardware_device_open,
	.release = hardware_device_close,
	.read = hardware_device_read,
	.unlocked_ioctl = hardware_device_ioctl
};

/*===============================================================================================*/
static int device_file_major_number = 0;
static const char device_name[] = "Hardware-Device";
struct task_struct *threadPtr;
static char buffer[BUF_LEN];
static bool is_running;
static bool is_halt;

int hardwareSim(void *data)
{
    int i;
    printk(KERN_INFO "hardwareSim:\n");
    for(i=0; i<BUF_LEN-1; ++i) {
        buffer[i]='a';
    }
    buffer[BUF_LEN-1]='\0';
    while(is_running) {
        while(!is_halt) {
            for(i=0; i<BUF_LEN-1; ++i) {
                ++buffer[i];
		if(buffer[i]>'z') buffer[i]='a';
            }
            buffer[BUF_LEN-1]='\0';
            msleep(1000);
        }
        msleep(1000);
    }
    return 0;
}

int register_device(void)
{
    int result = 0;

    printk( KERN_NOTICE "Hardware-Device: register_device() is called.\n" );

    result = register_chrdev( 0, device_name, &simple_driver_fops );
    if( result < 0 )
    {
        printk( KERN_WARNING "Hardware-Device:  can\'t register character device with errorcode = %i\n", result );
        return result;
    }

    device_file_major_number = result;
    printk( KERN_NOTICE "Hardware-Device: registered character device with major number = %i and minor numbers 0...255\n"
        , device_file_major_number );

    is_running=true;
    is_halt=false;
    printk(KERN_INFO "Hardware-Device: kthread_create(hardwareSim)\n");
    threadPtr = kthread_create(hardwareSim, NULL, "HardwareSimulator");
    if(threadPtr) {
        wake_up_process(threadPtr);
    }
    return 0;
}

/*===============================================================================================*/
void unregister_device(void)
{
    printk( KERN_NOTICE "Hardware-Device: unregister_device() is called\n" );
    is_running=false;
    is_halt=false;
    if(device_file_major_number != 0)
    {
        unregister_chrdev(device_file_major_number, device_name);
    }
}


/*===============================================================================================*/
static int hardware_device_open(struct inode *inode, struct file *file)
{
    printk(KERN_INFO "Hardware-Device: open() is called\n");
    return 0;
}

/*===============================================================================================*/
static int hardware_device_close(struct inode *inode, struct file *file)
{
    printk(KERN_INFO "Hardware-Device: close() is called\n");
    return 0;
}

/*===============================================================================================*/
static ssize_t hardware_device_read(struct file *filp, char __user *buf, size_t len, loff_t *off)
{
	//Check if the user buffer fits the device buffer
	//if(len == BUF_LEN) if we only want to read the entire block, but at that point
	//just using copy_to_user(buf, buffer, BUF_LEN) is simpler.
	//Not having a chance of copying too many (or too few) bytes back to the user buffer is nice, though.
	//The safety would be in assuring that the user does, in fact, know how many bytes the device
	//is working with, and not trying to copy back until we are sure of that. After that it's up to them
	//to be working with an appropriately sized buffer on their end.
	if(len <= BUF_LEN){
		//Call copy_to_user to copy the contents of the kernel space buffer, back to the user space buffer
		//using the specified buffer length from the function call
		unsigned long ret = copy_to_user(buf, buffer, len);
		//Report the read buffer contents
		printk(KERN_INFO "hardware_device_read: ret:%lu buffer:%s\n", ret, buffer);
		return len;
	}
	else{
		//Report that the buffer specified by the user is unfit for the device
		printk(KERN_INFO "hardware_device_read: user buffer not suitable for device");
		//Return 0 bytes read
		return 0;
	}
}

/*===============================================================================================*/
static long hardware_device_ioctl(struct file *file, unsigned int cmd, unsigned long arg)
{
	switch(cmd) {
		//In the case of the HARDWARE_DEVICE_HALT option in the cmd argument
		case HARDWARE_DEVICE_HALT:
			//Check if it has already halted
			if(!is_halt) {
				//Halt if not
				is_halt = true;
				//Report that it has halted
				printk(KERN_INFO "hardware_device_ioctl: device halted");
			}
			else {
				//Report that is has already halted
				printk(KERN_INFO "hardware_device_ioctl: device already halted");
			}

			break;
		//In the case of the HARDWARE_DEVICE_RESUME option in the cmd argument
		case HARDWARE_DEVICE_RESUME:
			//Check if it has halted
			if(is_halt) {
				//Resume if halted
				is_halt = false;
				//Report that is has resumed
				printk(KERN_INFO "hardware_device_ioctl: device resumed");
			}
			else {
				//Report that is was not halted to begin with
				printk(KERN_INFO "hardware_device_ioctl: device not halted");
			}

			break;
    }
    return 0;
}
