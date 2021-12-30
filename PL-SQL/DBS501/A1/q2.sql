ACCEPT desc PROMPT 'Enter the beginning of the Course Description in UPPER case:  '

DECLARE
    v_course_desc course.description%TYPE := '&desc';
    v_course_id course.course_no%TYPE;
    v_student_count NUMBER(4,0);
    ex_no_prereq EXCEPTION;
    ex_invalid_desc EXCEPTION;
    ex_too_many_students EXCEPTION;
    
    CURSOR c_course_cursor IS
        SELECT course_no 
        FROM course 
        WHERE UPPER(course.description) LIKE UPPER(v_course_desc)||'%';
    
    CURSOR c_section_cursor IS
        SELECT * 
        FROM section
        WHERE course_no IN (SELECT course_no
                            FROM course 
                            WHERE prerequisite 
                            IN (SELECT course_no 
                                FROM course 
                                WHERE UPPER(course.description)
                                LIKE UPPER(v_course_desc)||'%'))
        ORDER BY section_id;
    
    v_course_record c_course_cursor%ROWTYPE;
    c_section_record c_section_cursor%ROWTYPE;
    
BEGIN
    OPEN c_course_cursor;
    FETCH c_course_cursor INTO v_course_record;
    IF c_course_cursor%NOTFOUND THEN
        CLOSE c_course_cursor;
        RAISE ex_invalid_desc;
    ELSE
        OPEN c_section_cursor;
        FETCH c_section_cursor INTO c_section_record;
        IF c_section_cursor%NOTFOUND THEN
            CLOSE c_section_cursor;
            RAISE ex_no_prereq;
        ELSE
            CLOSE c_section_cursor;
            FOR ind IN c_section_cursor LOOP
                BEGIN
                    SELECT COUNT(*) INTO v_student_count FROM enrollment WHERE section_id = ind.section_id;
                    IF v_student_count >= 7 THEN
                        RAISE ex_too_many_students;
                    ELSE
                        DBMS_OUTPUT.PUT_LINE('There are ' || v_student_count ||' students for section ID ' || ind.section_id);
                    END IF;
                    
                    EXCEPTION
                        WHEN ex_too_many_students THEN
                        DBMS_OUTPUT.PUT_LINE('There are too many students for section ' || ind.section_id);
                        DBMS_OUTPUT.PUT_LINE('^^^^^^^^^^^^^^^^^^^^^^^^^^');
                END;
            END LOOP;
        END IF;
    END IF;
    
    EXCEPTION
        WHEN ex_invalid_desc THEN
            DBMS_OUTPUT.PUT_LINE('There is NO VALID course that starts on: ' || v_course_desc || '. Try again.');
        WHEN ex_no_prereq THEN
            DBMS_OUTPUT.PUT_LINE('There is NO PREREQUISITE course that starts on: ' || v_course_desc || '. Try again.');
END;
/