MERGE INTO Company
    (id,c_name,capital)
    KEY (id)
VALUES (1,'Apple',9999),
    (2,'Google',9999);


MERGE INTO Author
    (id,first_name,last_name)
    KEY(id)
VALUES (1,'Matteo','Rosso');
