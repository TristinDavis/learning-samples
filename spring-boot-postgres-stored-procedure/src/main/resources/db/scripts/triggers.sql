CREATE OR REPLACE FUNCTION helloWorld()
	RETURNS TRIGGER AS $$
 	BEGIN
 		RAISE INFO 'Hello World!';
 		RETURN NEW;
	END; $$
LANGUAGE PLPGSQL;

DROP TRIGGER IF EXISTS say_hello ON application_users;

CREATE TRIGGER say_hello BEFORE INSERT ON application_users FOR EACH ROW EXECUTE PROCEDURE helloWorld();