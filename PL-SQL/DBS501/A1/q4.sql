ACCEPT key_1 PROMPT 'Enter first key word to search for: '
ACCEPT key_2 PROMPT 'Enter second key word to search for: '

DECLARE
    v_key_1 VARCHAR2(50) := '&key_1';
    v_key_2 VARCHAR2(50) := '&key_2';
    ex_invalid_desc EXCEPTION;
    
    CURSOR c_course_cursor IS 
        SELECT * FROM course
        WHERE UPPER(description) LIKE '%'||UPPER(v_key_1)||'%'
        AND UPPER(description) LIKE '%'||UPPER(v_key_2)||'%';
    
    CURSOR c_section_cursor(c_id NUMBER) IS 
        SELECT d1.section_id, section_no, student_no 
        FROM section d1, (SELECT section_id, COUNT(*) AS student_no
                            FROM enrollment
                            GROUP BY section_id) d2
        WHERE d1.course_no = c_id AND d1.section_id = d2.section_id 
        ORDER BY section_id;
        /*I tried every option for odering these and couldn't get the same layout
        as the example no matter what, so I decided to just order them by the section ID.
        I hope this doesn't mean a loss of marks because I am really stumped on what the
        example is going by for its seemingly random sorting. 
        I think having them in orderis nicer anyway.*/
    v_course_record course%ROWTYPE;
BEGIN
    OPEN c_course_cursor;
    FETCH c_course_cursor INTO v_course_record;
    IF c_course_cursor%NOTFOUND THEN
        CLOSE c_course_cursor;
        RAISE ex_invalid_desc;
    ELSE
        CLOSE c_course_cursor;
        FOR ind IN c_course_cursor LOOP
            DBMS_OUTPUT.PUT_LINE(ind.course_no || ' ' || ind.description);
            DBMS_OUTPUT.PUT_LINE('**************************************');
            FOR indx IN c_section_cursor(ind.course_no) LOOP
                DBMS_OUTPUT.PUT_LINE('Section: '|| indx.section_no ||' has an enrollment of: ' || indx.student_no);
            END LOOP;
        END LOOP;
    END IF;
    
    EXCEPTION
        WHEN ex_invalid_desc THEN
            DBMS_OUTPUT.PUT_LINE('There is NO course containing these 2 words. Try again.');
END;
/