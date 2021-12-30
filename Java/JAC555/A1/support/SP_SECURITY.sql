CREATE OR REPLACE PACKAGE BODY P_SECURITY AS 
    FUNCTION F_SECURITY(P_SECID IN SECURITY.SEC_ID%TYPE, P_SECPASSWORD IN SECURITY.SEC_PASSWORD%TYPE) 
     RETURN NUMBER AS
        O_EMPLOYEE_ID SECURITY.EMPLOYEE_ID%TYPE;
    BEGIN
     
      SELECT EMPLOYEE_ID 
        INTO O_EMPLOYEE_ID
        FROM SECURITY
      WHERE SEC_ID = P_SECID AND SEC_PASSWORD = P_SECPASSWORD AND SEC_STATUS = 'A';
      
      RETURN O_EMPLOYEE_ID;
      
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
            
    END;
  
    PROCEDURE P_EMP_INFO(P_EMPLOYEE_ID IN EMPLOYEES.EMPLOYEE_ID%TYPE , P_INFO OUT cur_EmpInfo) AS 
    BEGIN
        OPEN P_INFO FOR
        SELECT  EMPLOYEE_ID ,
                FIRST_NAME ,
                LAST_NAME ,
                EMAIL ,
                PHONE ,
                HIRE_DATE ,
                MANAGER_ID ,
                JOB_TITLE
        
        FROM EMPLOYEES WHERE EMPLOYEE_ID = P_EMPLOYEE_ID;
    END;
END P_SECURITY;