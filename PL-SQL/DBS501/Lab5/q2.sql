CREATE OR REPLACE PROCEDURE show_bizdays
    (p_date DATE DEFAULT SYSDATE,
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
END;
/

--Not using default or sysdate for testing because sysdate is not 20-NOV-11
--Just using 20-NOV-11, 27-NOV-11 so I can get the output correct for the example
EXECUTE show_bizdays('20-NOV-11');
EXECUTE show_bizdays('27-NOV-11', 10);