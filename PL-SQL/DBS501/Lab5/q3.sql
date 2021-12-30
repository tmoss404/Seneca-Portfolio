CREATE OR REPLACE PACKAGE Lab5 IS

    FUNCTION get_desc (p_sec_id NUMBER) RETURN VARCHAR2;

    PROCEDURE show_bizdays (p_date DATE DEFAULT SYSDATE, p_days NUMBER DEFAULT 14);

END Lab5;
/

CREATE OR REPLACE PACKAGE BODY Lab5 IS

    FUNCTION get_desc(p_sec_id NUMBER)
    RETURN VARCHAR2 IS
    
    v_course_desc VARCHAR2(50);
    
    BEGIN
        SELECT description INTO v_course_desc FROM course WHERE course_no = 
            (SELECT course_no FROM section WHERE section_id = p_sec_id);
            
        RETURN 'Course Description for Section Id ' || p_sec_id || ' is ' || v_course_desc;
        
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RETURN 'There is NO such Section id: ' || p_sec_id;
    END get_desc;

    PROCEDURE show_bizdays (
        p_date DATE DEFAULT SYSDATE,
        p_days NUMBER DEFAULT 14)
    IS 
        v_indx NUMBER := 1;
        v_offset NUMBER :=0;
    BEGIN
        WHILE v_indx <= p_days LOOP
            IF TO_CHAR(p_date+v_offset, 'd') BETWEEN '2' AND '6' THEN
                DBMS_OUTPUT.PUT_LINE('The index is : ' || v_indx || ' and the table value is: ' || TO_CHAR(p_date+v_offset));
                v_indx := v_indx+1;
            END IF;
            v_offset := v_offset+1;
        END LOOP;
    END show_bizdays;

END Lab5;
/
--Using 20-NOV-11, 27-NOV-11 to show matching output
EXECUTE lab5.show_bizdays('20-NOV-11');
EXECUTE lab5.show_bizdays('27-NOV-11', 10);
--Using Default, sysdate+7 to show it working
EXECUTE lab5.show_bizdays();
EXECUTE lab5.show_bizdays(SYSDATE+7, 10);