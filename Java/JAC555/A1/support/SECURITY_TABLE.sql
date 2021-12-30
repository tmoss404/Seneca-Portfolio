CREATE TABLE SECURITY(
    EMPLOYEE_ID NUMBER(6,0) NOT NULL,
    SEC_ID VARCHAR2(20 byte) NOT NULL,
    SEC_PASSWORD varchar2(10 byte) NOT NULL,
    SEC_STATUS Char(1 byte) NOT NULL,
    constraint security_pk PRIMARY KEY(SEC_ID),
    constraint employeeid_fk FOREIGN KEY(EMPLOYEE_ID) REFERENCES EMPLOYEES(EMPLOYEE_ID),
    constraint employeeid_unq unique(EMPLOYEE_ID)
);