package validation;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="validationBean")
public class ValidationBean
{

	private String breakfast;
	private String coffee;
	private String tea;
	private String deliveryTakeOut;
   
	// Breakfast Getter / Setter
    public String getBreakfast() {
		return breakfast;
	}
   
	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}
	
	
	// Coffee Getter / Setter
	public String getCoffee() {
		return coffee;
	}
	
	public void setCoffee(String coffee) {
		this.coffee = coffee;
	}
	
	// Tea Getter / Setter
	public String getTea() {
		return tea;
	}
	
	public void setTea(String tea) {
		this.tea = tea;
	}
	
	// DeliveryTakeOut Getter / Setter
    public String getDeliveryTakeOut() {
		return deliveryTakeOut;
	}

	public void setDeliveryTakeOut(String deliveryTakeOut) {
		this.deliveryTakeOut = deliveryTakeOut;
	}

public String getResult()
   {
      if (breakfast != null && coffee != null && tea != null)
         return "Thank you, your order is placed!";
      else
         return ""; // request has not yet been made
   } 
}
