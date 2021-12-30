CREATE OR REPLACE FUNCTION valid_stud(p_st_id NUMBER)
RETURN BOOLEAN
IS
    v_exists NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_exists FROM student WHERE student_id = p_st_id;
    IF v_exists = 0 THEN
        RETURN FALSE;
    ELSE
        RETURN TRUE;
    END IF;
END;
/

CREATE OR REPLACE PACKAGE manage_stud IS
    
    PROCEDURE find_stud (p_st_id IN NUMBER, p_lname OUT VARCHAR2, p_phone OUT VARCHAR2, p_zip OUT VARCHAR2);
    --Overloaded Q4 find_stud
    PROCEDURE find_stud (p_st_id IN NUMBER, p_fname OUT VARCHAR2, p_lname OUT VARCHAR2);
    -------------------------
    PROCEDURE drop_stud (p_st_id IN NUMBER, p_flag IN CHAR := 'R');
    
END manage_stud;
/

CREATE OR REPLACE PACKAGE BODY manage_stud IS
    PROCEDURE find_stud (
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
    END find_stud;
    
    ----------------------------------
    --Start Overloaded Q4 find_stud
    PROCEDURE find_stud (
        p_st_id IN NUMBER,
        p_fname OUT VARCHAR2,
        p_lname OUT VARCHAR2)
    IS
        v_phone student.phone%TYPE;
        v_zip student.zip%TYPE;
    BEGIN
        CASE valid_stud(p_st_id)
        WHEN TRUE THEN
            SELECT first_name, last_name, phone, zip 
            INTO p_fname, p_lname, v_phone, v_zip 
            FROM student 
            WHERE student_id = p_st_id;
        
            DBMS_OUTPUT.PUT_LINE('Student with the Id of: ' || p_st_id ||
                                 ' is ' || p_lname ||
                                 ' with the phone# ' || v_phone ||
                                 ' and who belongs to zip code ' || v_zip
                                 );
        WHEN FALSE THEN
            DBMS_OUTPUT.PUT_LINE('There is NO Student with the Id of : ' || p_st_id);
        END CASE;
    END find_stud;
    --End Overloaded Q4 find_stud
    ----------------------------------
    
    PROCEDURE drop_stud (
        p_st_id IN NUMBER,
        p_flag IN CHAR := 'R') 
    IS
        v_count NUMBER;
        v_rows_removed NUMBER := 0;
        ex_no_such_student EXCEPTION;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM student WHERE student_id = p_st_id;
        IF v_count > 0 THEN
            CASE UPPER(p_flag)
                WHEN 'C' THEN
                    DELETE FROM grade WHERE student_id = p_st_id;
                    v_rows_removed := v_rows_removed + SQL%ROWCOUNT;
                    DELETE FROM enrollment WHERE student_id = p_st_id;
                    v_rows_removed := v_rows_removed + SQL%ROWCOUNT;
                    DELETE FROM student WHERE student_id = p_st_id;
                    v_rows_removed := v_rows_removed + SQL%ROWCOUNT;
                    
                    DBMS_OUTPUT.PUT_LINE('Student with the Id of : '|| p_st_id || 
                                         ' is removed. Total # of rows deleted is: '|| v_rows_removed);
                WHEN 'R' THEN
                    SELECT COUNT(*) INTO v_count FROM enrollment WHERE student_id = p_st_id;
                    IF v_count = 0 THEN
                        DELETE FROM student WHERE student_id = p_st_id;
                        DBMS_OUTPUT.PUT_LINE('Student with the Id of : '|| p_st_id ||' is removed. He/she was NOT enrolled in any courses.');
                    ELSE
                        DBMS_OUTPUT.PUT_LINE('Student with the Id of : '|| p_st_id ||' is enrolled in or more courses and his/her removal is denied.');
                    END IF;
            END CASE;
        ELSE 
            RAISE ex_no_such_student;
        END IF;
    
        EXCEPTION
            WHEN ex_no_such_student THEN
                DBMS_OUTPUT.PUT_LINE('Student with the Id of : '|| p_st_id ||' does NOT exist. Try again. ');
    END drop_stud;
    
END manage_stud;
/

VARIABLE lname VARCHAR2;
VARIABLE fname VARCHAR2;

EXECUTE manage_stud.find_stud(110, :fname, :lname);
EXECUTE manage_stud.find_stud(99, :fname, :lname);