INSERT INTO company (ID, name, address, created_by, created_at, updated_by, updated_at) VALUES
  (10, 'tomato', 'house', 'john', {ts '2020-04-26 18:47:52.69'}, 'owen', {ts '2020-04-26 20:47:52.69'}),
  (11, 'banana', 'zoo', 'tom', {ts '2020-04-26 18:47:52.69'}, 'john', {ts '2020-04-26 18:47:52.69'}),
  (12, 'apple', 'flower', 'john', {ts '2020-04-26 18:47:52.69'}, 'owen', {ts '2020-04-26 20:47:52.69'}),
  (13, 'cucumber', 'taipei', 'tom', {ts '2020-04-26 18:47:52.69'}, 'john', {ts '2020-04-26 18:47:52.69'});


INSERT INTO client (ID, name, email, phone, company_id, created_by, created_at, updated_by, updated_at) VALUES
  (14, 'tomato', 'john@gmail.com', '0912123456', 10, 'john', {ts '2020-04-26 18:47:52.69'}, 'owen', {ts '2020-04-26 20:47:52.69'}),
  (15, 'banana', 'tom@gmail.com', '0912123456', 11, 'tom', {ts '2020-04-26 18:47:52.69'}, 'john', {ts '2020-04-26 18:47:52.69'});