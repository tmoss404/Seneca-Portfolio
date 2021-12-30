CREATE OR REPLACE PROCEDURE drop_stud (
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
END;
/
--Testing execution
EXECUTE drop_stud(210, 'R');
SELECT section_id, final_grade FROM enrollment WHERE student_id = 210;
SELECT first_name, last_name FROM student WHERE student_id = 210;

EXECUTE drop_stud(410);

EXECUTE drop_stud(310, 'R');
SELECT first_name, last_name FROM student WHERE student_id = 310;
ROLLBACK;
SELECT first_name, last_name FROM student WHERE student_id = 310;

EXECUTE drop_stud(110, 'C');
SELECT section_id, final_grade FROM enrollment WHERE student_id = 110;
SELECT first_name, last_name FROM student WHERE student_id = 110;
ROLLBACK;
SELECT section_id, final_grade FROM enrollment WHERE student_id = 110;
SELECT first_name, last_name FROM student WHERE student_id = 110;

