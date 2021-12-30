CREATE OR REPLACE PROCEDURE find_stud (
    p_st_id IN NUMBER,
    p_lname OUT VARCHAR2,
    p_phone OUT VARCHAR2,
    p_zip OUT VARCHAR2) 
IS

BEGIN
    SELECT last_name, phone, zip 
        INTO p_lname, p_phone, p_zip 
        FROM student 
        WHERE student_id = p_st_id;
    
    DBMS_OUTPUT.PUT_LINE('Student with the Id of: ' || p_st_id ||
                         ' is ' || p_lname ||
                         ' with the phone# ' || p_phone ||
                         ' and who belongs to zip code ' || p_zip
                         );
    
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('There is NO Student with the Id of : ' || p_st_id);
END;
/

VARIABLE lname VARCHAR2;
VARIABLE zip VARCHAR2;
VARIABLE phone VARCHAR2;

EXECUTE find_stud(110, :lname, :phone, :zip);

EXECUTE find_stud(99, :lname, :phone, :zip);