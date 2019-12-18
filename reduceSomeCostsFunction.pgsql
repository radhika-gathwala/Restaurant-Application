CREATE OR REPLACE FUNCTION reduceSomeCostsFunction(maxVisitCount integer) RETURNS integer AS $$

DECLARE
theVisitID integer;
updatedRow integer:= 0;

DECLARE highC cursor for
SELECT visitID FROM visit v, customer c 
WHERE v.custID = c.custID AND c.status = 'H' AND v.cost IS NOT NULL
ORDER BY c.joinDate;

DECLARE medC cursor for
SELECT visitID FROM visit v, customer c
WHERE v.custID = c.custID AND c.status = 'M' AND v.cost IS NOT NULL
ORDER BY c.joinDate;

DECLARE lowC cursor for
SELECT visitID FROM visit v, customer c 
WHERE v.custID = c.custID AND c.status = 'L' AND v.cost IS NOT NULL
ORDER BY c.joinDate;




BEGIN
    OPEN highC;
	LOOP
		FETCH highC into theVisitID;
		EXIT WHEN NOT FOUND OR updatedRow = maxVisitCount;
		UPDATE visit
		SET cost = cost * 0.9
		WHERE visitID = theVisitID;
		updatedRow := updatedRow  + 1;
    END LOOP;
    CLOSE highC;
    OPEN medC;
    LOOP
        FETCH medC into theVisitID;
		EXIT WHEN NOT FOUND OR updatedRow = maxVisitCount;
		UPDATE visit
		SET cost = cost * 0.95
		WHERE visitID = theVisitID;
		updatedRow := updatedRow  + 1;
    END LOOP;
    CLOSE medC;
    OPEN lowC;
    LOOP
        FETCH lowC into theVisitID;
		EXIT WHEN NOT FOUND OR updatedRow = maxVisitCount;
        UPDATE visit
		SET cost = cost * 0.99
		WHERE visitID = theVisitID;
		updatedRow := updatedRow  + 1;
	END LOOP;
	CLOSE lowC;
RETURN updatedRow;
END;
$$ LANGUAGE PLPGSQL;