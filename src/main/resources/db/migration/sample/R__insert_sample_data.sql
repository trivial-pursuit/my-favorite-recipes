TRUNCATE TABLE categories CASCADE;

-- CATEGORIES
INSERT INTO categories (id, title, description)
VALUES ('00000000-aaaa-4bbb-8ccc-111111111111', 'Kuchen', 'Vom Rührkuchen bis zur grandiosen Torte'),
       ('00000000-aaaa-4bbb-8ccc-111111111112', 'Warme Gerichte', null),
       ('00000000-aaaa-4bbb-8ccc-111111111113', 'Nachtisch', 'Süßes zum Genießen');
