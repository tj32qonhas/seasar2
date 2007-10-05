CREATE TABLE DEPARTMENT(DEPARTMENT_ID NUMERIC(8) NOT NULL PRIMARY KEY, DEPARTMENT_NO NUMERIC(2) NOT NULL,DEPARTMENT_NAME VARCHAR(20),LOCATION VARCHAR(20), VERSION NUMERIC(8));
CREATE TABLE ADDRESS(ADDRESS_ID NUMERIC(8) NOT NULL PRIMARY KEY, STREET VARCHAR(20), VERSION NUMERIC(8));
CREATE TABLE EMPLOYEE(EMPLOYEE_ID NUMERIC(8) NOT NULL PRIMARY KEY, EMPLOYEE_NO NUMERIC(4) NOT NULL ,EMPLOYEE_NAME VARCHAR(20),MANAGER_ID NUMERIC(8),HIREDATE DATE,SALARY NUMERIC(7,2),DEPARTMENT_ID NUMERIC(8),ADDRESS_ID NUMERIC(8), VERSION NUMERIC(8), CONSTRAINT FK_DEPARTMENT_ID FOREIGN KEY(DEPARTMENT_ID) REFERENCES DEPARTMENT(DEPARTMENT_ID),CONSTRAINT FK_ADDRESS_ID FOREIGN KEY(ADDRESS_ID) REFERENCES ADDRESS(ADDRESS_ID));

INSERT INTO DEPARTMENT VALUES(1,10,'ACCOUNTING','NEW YORK',1);
INSERT INTO DEPARTMENT VALUES(2,20,'RESEARCH','DALLAS',1);
INSERT INTO DEPARTMENT VALUES(3,30,'SALES','CHICAGO',1);
INSERT INTO DEPARTMENT VALUES(4,40,'OPERATIONS','BOSTON',1);
INSERT INTO ADDRESS VALUES(1,'STREET 1',1);
INSERT INTO ADDRESS VALUES(2,'STREET 2',1);
INSERT INTO ADDRESS VALUES(3,'STREET 3',1);
INSERT INTO ADDRESS VALUES(4,'STREET 4',1);
INSERT INTO ADDRESS VALUES(5,'STREET 5',1);
INSERT INTO ADDRESS VALUES(6,'STREET 6',1);
INSERT INTO ADDRESS VALUES(7,'STREET 7',1);
INSERT INTO ADDRESS VALUES(8,'STREET 8',1);
INSERT INTO ADDRESS VALUES(9,'STREET 9',1);
INSERT INTO ADDRESS VALUES(10,'STREET 10',1);
INSERT INTO ADDRESS VALUES(11,'STREET 11',1);
INSERT INTO ADDRESS VALUES(12,'STREET 12',1);
INSERT INTO ADDRESS VALUES(13,'STREET 13',1);
INSERT INTO ADDRESS VALUES(14,'STREET 14',1);
INSERT INTO EMPLOYEE VALUES(1,7369,'SMITH',13,'1980-12-17',800,2,1,1);
INSERT INTO EMPLOYEE VALUES(2,7499,'ALLEN',6,'1981-02-20',1600,3,2,1);
INSERT INTO EMPLOYEE VALUES(3,7521,'WARD',6,'1981-02-22',1250,3,3,1);
INSERT INTO EMPLOYEE VALUES(4,7566,'JONES',9,'1981-04-02',2975,2,4,1);
INSERT INTO EMPLOYEE VALUES(5,7654,'MARTIN',6,'1981-09-28',1250,3,5,1);
INSERT INTO EMPLOYEE VALUES(6,7698,'BLAKE',9,'1981-05-01',2850,3,6,1);
INSERT INTO EMPLOYEE VALUES(7,7782,'CLARK',9,'1981-06-09',2450,1,7,1);
INSERT INTO EMPLOYEE VALUES(8,7788,'SCOTT',4,'1982-12-09',3000.0,2,8,1);
INSERT INTO EMPLOYEE VALUES(9,7839,'KING',NULL,'1981-11-17',5000,1,9,1);
INSERT INTO EMPLOYEE VALUES(10,7844,'TURNER',6,'1981-09-08',1500,3,10,1);
INSERT INTO EMPLOYEE VALUES(11,7876,'ADAMS',8,'1983-01-12',1100,2,11,1);
INSERT INTO EMPLOYEE VALUES(12,7900,'JAMES',6,'1981-12-03',950,3,12,1);
INSERT INTO EMPLOYEE VALUES(13,7902,'FORD',4,'1981-12-03',3000,2,13,1);
INSERT INTO EMPLOYEE VALUES(14,7934,'MILLER',7,'1982-01-23',1300,1,14,1);