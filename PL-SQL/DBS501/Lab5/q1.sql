SET SERVEROUTPUT ON
SET VERIFY OFF

CREATE OR REPLACE FUNCTION get_desc(p_sec_id NUMBER)
RETURN VARCHAR2 IS

v_course_desc VARCHAR2(50);

BEGIN

    SELECT description INTO v_course_desc FROM course WHERE course_no = 
        (SELECT course_no FROM section WHERE section_id = p_sec_id);
        
    RETURN 'Course Description for Section Id ' || p_sec_id || ' is ' || v_course_desc;
    
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 'There is NO such Section id: ' || p_sec_id;
            
END;


VARIABLE sec_id_test NUMBER;

BEGIN
    :sec_id_test := 150;
    DBMS_OUTPUT.PUT_LINE( get_desc(:sec_id_test) );
    
    :sec_id_test := 999;
    DBMS_OUTPUT.PUT_LINE( get_desc(:sec_id_test) );
END;