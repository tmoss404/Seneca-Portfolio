ACCEPT desc PROMPT 'Enter the beginning of the Course Description in UPPER case:  '

DECLARE
    v_course_desc VARCHAR2(50) := '&desc';
    ex_no_prereqs EXCEPTION;
    ex_invalid_desc EXCEPTION;
    v_no_prereq NUMBER(4,0) := 0;
    v_course_count NUMBER(4,0) := 0;
    
    CURSOR c_course_cursor IS
        SELECT * 
        FROM course 
        WHERE UPPER(course.description) LIKE UPPER(v_course_desc)||'%';
        
    CURSOR c_prereq_cursor(prereq_no NUMBER) IS SELECT *
                FROM course
                WHERE course.course_no = prereq_no;
                
    TYPE course_info IS RECORD (
        c_id NUMBER(38,0),
        c_desc VARCHAR2(50),
        c_cost NUMBER(9,2),
        prereq course%ROWTYPE
    );
    
    v_course_record c_course_cursor%ROWTYPE;
    v_course course_info;
BEGIN
    OPEN c_course_cursor;
    FETCH c_course_cursor INTO v_course_record;
    IF c_course_cursor%NOTFOUND THEN
        CLOSE c_course_cursor;
        RAISE ex_invalid_desc;
    ELSE
        CLOSE c_course_cursor;
        FOR ind IN c_course_cursor LOOP
            v_course.c_id := ind.course_no;
            v_course.c_desc := ind.description;
            v_course.c_cost := ind.cost;
            OPEN c_prereq_cursor(ind.prerequisite);
            FETCH c_prereq_cursor INTO v_course.prereq;
            IF(c_prereq_cursor%NOTFOUND) THEN
                --counting a selected course that does not have a prereq
                v_no_prereq := v_no_prereq + 1;
                CLOSE c_prereq_cursor;
            ELSE
                CLOSE c_prereq_cursor;
                DBMS_OUTPUT.PUT_LINE('Course: ' || v_course.c_id || ' - ' || v_course.c_desc);
                DBMS_OUTPUT.PUT_LINE('Cost: ' || v_course.c_cost);
                DBMS_OUTPUT.PUT_LINE('Prerequisite: ' || v_course.prereq.course_no || ' - ' || v_course.prereq.description);
                DBMS_OUTPUT.PUT_LINE('Prerequisite Cost: ' || v_course.prereq.cost);
                DBMS_OUTPUT.PUT_LINE('========================================');
            END IF;
            --storing the final count of selected courses to compare to the number or missing prereqs
            v_course_count := c_course_cursor%ROWCOUNT;
        END LOOP;
        /*I set this up to ensure that I really am checking for NO prerequisites on ANY of the
        selected courses before throwing an exception that says 'for ANY course that starts on'.
        I know it's not in the example output, but if someone entered 'INTRO' and it said something
        like that when only one is missing a prereq it would have bothered me.*/
        IF v_course_count = v_no_prereq THEN
            RAISE ex_no_prereqs;
        END IF;
    END IF;
    
    EXCEPTION
        WHEN ex_invalid_desc THEN
            DBMS_OUTPUT.PUT_LINE('There is NO VALID course that starts on: ' || v_course_desc || '. Try again.');
        WHEN ex_no_prereqs THEN
            DBMS_OUTPUT.PUT_LINE('There is NO prerequisite course for any course that starts on ' || v_course_desc || '. Try again.');
END;
/