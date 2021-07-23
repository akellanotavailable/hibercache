USE test1;

LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES ('037e6f75-ba6b-433b-b72d-2599632d68b7','RztqspOLIh','USER'),('2f971375-5269-4cf6-a9d8-783eedd1c2dc','rxk3BKwCKP','USER'),('42f0510c-d29a-41e8-9ee3-ca18495f17c2','zGdFTaIfBt','USER'),('5e449b5e-daa1-4fd4-ad76-03d793afc9ed','SomeUser','USER'),('789f663f-5b50-4d3a-b408-7dda309f06e6','SomeUser','USER'),('a655e209-7a9d-45cc-967d-ad9199ec28bc','7VfdqpBz8x','USER'),('c9c636b6-e519-4ce5-9bbc-04411138081c','admin','ADMIN'),('ee590603-401a-415b-8c4c-8899fad39a22','tG1aF2L6tl','USER');
UNLOCK TABLES;

LOCK TABLES `request` WRITE;
INSERT INTO `request` VALUES ('20bfd3a7-5032-4f6f-b3e1-2590e29e2d4e',NULL,'test1', 'c9c636b6-e519-4ce5-9bbc-04411138081c'),('a29fd108-3884-40e0-bc97-17215f977980',777.00,'updatedName', 'c9c636b6-e519-4ce5-9bbc-04411138081c');
UNLOCK TABLES;
