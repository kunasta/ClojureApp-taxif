
--name: update!
-- update an existing route
UPDATE routes SET
start = :start
 end = :end
 price = :price
 date = :date
WHERE id = :id

