INSERT INTO Company (c_name,capital)
    select t.*
    from ((SELECT  'Apple' as c_name, 9999 as capital
          ) union all
          (SELECT 'Google', 9999
          )
         ) t
WHERE NOT EXISTS (
    SELECT * FROM Company
);


INSERT INTO Author (first_name,last_name)
    SELECT 'Matteo','Rosso'
WHERE NOT EXISTS (
    SELECT * FROM Author
);
