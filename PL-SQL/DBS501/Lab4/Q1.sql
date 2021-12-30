SET SERVEROUTPUT ON
SET VERIFY OFF

CREATE OR REPLACE PROCEDURE mine (v_date VARCHAR2, v_character CHAR) IS
    ex_wrong_char EXCEPTION; 
    v_day VARCHAR2(20);
    v_count NUMBER;
BEGIN
    SELECT TO_CHAR(LAST_DAY(TO_DATE(v_date, 'mm/yy')), 'DAY') INTO v_day FROM dual;
    DBMS_OUTPUT.PUT_LINE('Last day of the month ' || v_date || ' is ' || v_day);
    CASE
        WHEN UPPER(v_character) = 'F' THEN
            SELECT COUNT(*) INTO v_count FROM user_objects WHERE object_type = 'FUNCTION';
            DBMS_OUTPUT.PUT_LINE('Number of stored objects of type F is ' || v_count);
        WHEN UPPER(v_character) = 'P' THEN
            SELECT COUNT(*) INTO v_count FROM user_objects WHERE object_type = 'PROCEDURE';
            DBMS_OUTPUT.PUT_LINE('Number of stored objects of type P is ' || v_count);
        WHEN UPPER(v_character) = 'B' THEN
            SELECT COUNT(*) INTO v_count FROM user_objects WHERE object_type = 'PACKAGE';
            DBMS_OUTPUT.PUT_LINE('Number of stored objects of type B is ' || v_count);
        ELSE
            RAISE ex_wrong_char;
    END CASE;
    
    EXCEPTION
        WHEN ex_wrong_char THEN
            DBMS_OUTPUT.PUT_LINE('You have entered an Invalid letter for the stored object. Try P, F or B.');
        WHEN others THEN
            DBMS_OUTPUT.PUT_LINE('You have entered an Invalid FORMAT for the MONTH and YEAR. Try MM/YY. ');
END mine;