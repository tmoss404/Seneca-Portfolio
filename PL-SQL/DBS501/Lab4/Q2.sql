CREATE OR REPLACE PROCEDURE add_zip 
    (p_zip IN zipcode.zip%TYPE, 
     p_city IN zipcode.city%TYPE,
     p_state IN zipcode.city%TYPE,
     p_flag OUT VARCHAR2,
     p_zipnum OUT NUMBER) IS
    
    ex_zip_exists EXCEPTION;
    v_zip_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_zip_count FROM zipcode WHERE zip = p_zip;
    IF v_zip_count > 0 THEN
        p_flag := 'FAILURE';
        SELECT COUNT(*) INTO p_zipnum FROM zipcode WHERE state = p_state;
        RAISE ex_zip_exists;
    ELSE
        p_flag := 'SUCCESS';
        INSERT INTO zipcode VALUES (p_zip, p_city, p_state, USER(), SYSDATE(), USER(), SYSDATE());
        SELECT COUNT(*) INTO p_zipnum FROM zipcode WHERE state = p_state;
    END IF;
    
    EXCEPTION
        WHEN ex_zip_exists THEN
            DBMS_OUTPUT.PUT_LINE('This ZIPCODE ' || p_zip || ' is already in the Database. Try again. ');
END add_zip;