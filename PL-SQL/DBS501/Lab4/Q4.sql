CREATE OR REPLACE FUNCTION instruct_status(p_fname VARCHAR2, p_lname VARCHAR2)
RETURN VARCHAR2 IS

v_ins_count NUMBER;
v_sec_count NUMBER;

BEGIN
    SELECT COUNT(*) INTO v_ins_count FROM instructor 
    WHERE UPPER(first_name) = p_fname AND UPPER(last_name) = p_lname;
    
    IF(v_ins_count > 0) THEN
        SELECT COUNT(*) INTO v_sec_count 
        FROM section WHERE instructor_id = 
            (SELECT instructor_id FROM instructor WHERE 
             UPPER(first_name) = p_fname AND UPPER(last_name) = p_lname);
        CASE 
            WHEN v_sec_count > 9 THEN
                RETURN 'This Instructor will teach ' || v_sec_count || ' courses and needs a vacation';
            WHEN v_sec_count = 0 THEN
                RETURN 'This Instructor is NOT scheduled to teach';
            ELSE
                RETURN 'This Instructor will teach ' || v_sec_count || ' courses';
        END CASE;
    ELSE    
        RETURN 'There is NO such instructor.';
    END IF;      
END;