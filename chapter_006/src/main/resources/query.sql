--1. Написать запрос получение всех продуктов с типом "СЫР"
SELECT * 
	FROM product p, type t
	WHERE p.type_id = t.id AND
		t.name = 'СЫР';
--2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
SELECT *
	FROM product p
	WHERE name LIKE '%мороженное%';
	
--3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
SELECT * FROM product
	WHERE expired_date BETWEEN date_trunc('month', NOW())::date + interval '1 month' 
		AND ((date_trunc('month', NOW()) + interval '2 month') - interval '1 day')::date;

--4. Написать запрос, который вывод самый дорогой продукт.
SELECT * 
	FROM product 
	WHERE price = (
		SELECT MAX(price) FROM product
		);

--5. Написать запрос, который выводит количество всех продуктов определенного типа.

SELECT COUNT(*) 
	FROM product p, type t
	WHERE p.type_id = t.id AND
		t.name = 'СЫР';
		
--6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT *
	FROM product p, type t
	WHERE p.type_id = t.id AND
		t.name IN ('СЫР', 'МОЛОКО') ;

--7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.  
SELECT t.name, Count(*) "count"
	FROM "product" p, "type" t
	WHERE p.type_id = t.id 
	GROUP BY t.name
	HAVING Count(*) < 10;

--8. Вывести все продукты и их тип.
SELECT p.*, t.name
	FROM "product" p, "type" t
	WHERE p.type_id = t.id ;