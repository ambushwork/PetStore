-- Cleanup
DELETE FROM T_ORDER_LINE;
DELETE FROM T_ORDER;
DELETE FROM T_CUSTOMER;
DELETE FROM T_ITEM;
DELETE FROM T_PRODUCT;
DELETE FROM T_CATEGORY;

-- Load
INSERT INTO T_CATEGORY (id, name, description) VALUES ('FISH', 'Fish', 'Any of numerous cold-blooded aquatic vertebrates characteristically having fins, gills, and a streamlined body' );
INSERT INTO T_CATEGORY (id, name, description) VALUES ('DOGS', 'Dogs', 'A domesticated carnivorous mammal related to the foxes and wolves and raised in a wide variety of breeds' );
INSERT INTO T_CATEGORY (id, name, description) VALUES ('REPTILES', 'Reptiles', 'Any of various cold-blooded, usually egg-laying vertebrates, such as a snake, lizard, crocodile, turtle' );
INSERT INTO T_CATEGORY (id, name, description) VALUES ('CATS', 'Cats', ' Small carnivorous mammal domesticated since early times as a catcher of rats and mice and as a pet and existing in several distinctive breeds and varieties' );
INSERT INTO T_CATEGORY (id, name, description) VALUES ('BIRDS', 'Birds', 'Any of the class Aves of warm-blooded, egg-laying, feathered vertebrates with forelimbs modified to form wings' );

INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('FISW01', 'Angelfish', 'Saltwater fish from Australia', 'FISH');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('FISW02', 'Tiger Shark', 'Saltwater fish from Australia', 'FISH');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('FIFW01', 'Koi', 'Freshwater fish from Japan', 'FISH');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('FIFW02', 'Goldfish', 'Freshwater fish from China', 'FISH');

INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('K9BD01', 'Bulldog', 'Friendly dog from England', 'DOGS');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('K9PO02', 'Poodle', 'Cute dog from France', 'DOGS');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('K9DL01', 'Dalmation', 'Great dog for a fire station', 'DOGS');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('K9RT01', 'Golden Retriever', 'Great family dog', 'DOGS');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('K9RT02', 'Labrador Retriever', 'Great hunting dog', 'DOGS');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('K9CW01', 'Chihuahua', 'Great companion dog', 'DOGS');

INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('RPSN01', 'Rattlesnake', 'Doubles as a watch dog', 'REPTILES');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('RPLI02', 'Iguana', 'Friendly green friend', 'REPTILES');

INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('FLDSH01', 'Manx', 'Great for reducing mouse populations', 'CATS');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('FLDLH02', 'Persian', 'Friendly house cat, doubles as a princess', 'CATS');

INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('AVCB01', 'Amazon Parrot', 'Great companion for up to 75 years', 'BIRDS');
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES ('AVSB02', 'Finch', 'Great stress reliever', 'BIRDS');

INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST1', 'Large', '10.00', 'fish1.jpg', 'FISW01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST2', 'Thootless', '10.00', 'fish1.jpg', 'FISW01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST3', 'Spotted', '12.00', 'fish4.jpg', 'FISW02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST4', 'Spotless', '12.00', 'fish4.jpg', 'FISW02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST5', 'Male Adult', '12.00', 'fish3.jpg', 'FIFW01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST6', 'Female Adult', '12.00', 'fish3.jpg', 'FIFW01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST7', 'Male Puppy', '12.00', 'fish2.jpg', 'FIFW02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST8', 'Female Puppy', '12.00', 'fish2.jpg', 'FIFW02');

INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST9', 'Spotless Male Puppy', '22.00', 'dog1.jpg', 'K9BD01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST10', 'Spotless Female Puppy', '22.00', 'dog1.jpg', 'K9BD01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST11', 'Spotted Male Puppy', '32.00', 'dog2.jpg', 'K9PO02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST12', 'Spotted Female Puppy', '32.00', 'dog2.jpg', 'K9PO02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST13', 'Tailed', '62.00', 'dog3.jpg', 'K9DL01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST14', 'Tailless', '62.00', 'dog3.jpg', 'K9DL01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST15', 'Tailed', '82.00', 'dog4.jpg', 'K9RT01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST16', 'Tailless', '82.00', 'dog4.jpg', 'K9RT01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST17', 'Tailed', '100.00', 'dog5.jpg', 'K9RT02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST18', 'Tailless', '100.00', 'dog5.jpg', 'K9RT02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST19', 'Female Adult', '100.00', 'dog6.jpg', 'K9CW01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST20', 'Female Adult', '100.00', 'dog6.jpg', 'K9CW01');

INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST21', 'Female Adult', '20.00', 'reptile1.jpg', 'RPSN01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST22', 'Male Adult', '20.00', 'reptile1.jpg', 'RPSN01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST211', 'Female Adult', '150.00', 'lizard1.jpg', 'RPLI02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST221', 'Male Adult', '160.00', 'lizard1.jpg', 'RPLI02');

INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST23', 'Male Adult', '120.00', 'cat1.jpg','FLDSH01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST24', 'Female Adult', '120.00', 'cat1.jpg', 'FLDSH01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST231', 'Male Adult', '70.00', 'cat2.jpg','FLDLH02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST241', 'Female Adult', '90.00', 'cat2.jpg', 'FLDLH02');

INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST25', 'Male Adult', '120.00', 'bird2.jpg', 'AVCB01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST26', 'Female Adult', '120.00', 'bird2.jpg', 'AVCB01');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST27', 'Male Adult', '75.00', 'bird1.jpg', 'AVSB02');
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES ('EST29', 'Female Adult', '80.00', 'bird1.jpg', 'AVSB02');

INSERT INTO T_CUSTOMER (id, firstname, lastname, telephone, street1, street2, city, state, zipcode, country, creditcardnumber, creditcardtype, creditcardexpirydate, email, password) VALUES ('marc123', 'Marc', 'Fleury', '545 123 45', '65 Ritherdon Road', '', 'Los Angeles', 'LA', '56421', 'USA', '', '', '', '','marc123');
INSERT INTO T_CUSTOMER (id, firstname, lastname, telephone, street1, street2, city, state, zipcode, country, creditcardnumber, creditcardtype, creditcardexpirydate, email, password) VALUES ('bill000', 'Bill', 'Gates', '654 046 12', '27 West Side', 'Story', 'Alhabama', 'Texas', '8401', 'USA', '1231 4564 2222', 'Visa', '02/18', 'bill.gates@microsoft.com', 'bill000');
INSERT INTO T_CUSTOMER (id, firstname, lastname, telephone, street1, street2, city, state, zipcode, country, creditcardnumber, creditcardtype, creditcardexpirydate, email, password) VALUES ('job5', 'Steve', 'Jobs', '548 157 15', '154 Star Boulevard', '', 'San Francisco', 'WC', '5455', 'USA', '', '', '', 'steve.jobs@apple.com', 'job5');
