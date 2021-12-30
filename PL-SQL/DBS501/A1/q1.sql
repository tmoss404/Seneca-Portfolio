ACCEPT city PROMPT 'Please provide the valid city without department: '

DECLARE
    v_city_name VARCHAR2(20) := '&city';
    v_loc_id NUMBER(4,0);
    v_dept_count NUMBER(4,0);
    v_dept_id NUMBER(4,0);
    v_manager_id NUMBER(4,0);
BEGIN
    SELECT location_id 
    INTO v_loc_id
    FROM locations 
    WHERE UPPER(v_city_name) = UPPER(city);
    
        SELECT COUNT(*) INTO v_dept_count
        FROM departments
        WHERE location_id = v_loc_id;
        
        CASE
            WHEN v_dept_count = 0 THEN
                SELECT MAX(department_id) INTO v_dept_id FROM departments;
                v_dept_id := v_dept_id + 50;
                
                SELECT manager_id 
                INTO v_manager_id
                FROM employees
                GROUP BY manager_id
                HAVING COUNT(*) = (
                    SELECT MAX(COUNT(manager_id))
                    FROM employees
                    GROUP BY manager_id);
                    
                INSERT INTO departments 
                    VALUES (v_dept_id, 'Testing', v_manager_id, v_loc_id);
            WHEN v_dept_count = 1 THEN
                DBMS_OUTPUT.PUT_LINE('This city already contains department: ' || v_city_name);
            WHEN v_dept_count > 1 THEN
                DBMS_OUTPUT.PUT_LINE('This city has MORE THAN ONE department: ' || v_city_name);
        END CASE;
        
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
    DBMS_OUTPUT.PUT_LINE('This city is NOT listed: ' || v_city_name);
END;
/
SELECT * FROM departments WHERE department_id = 320;
ROLLBACK