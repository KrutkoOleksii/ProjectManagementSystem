INSERT INTO hw2.companies VALUES ('1', 'Superlogic', '12121212');
INSERT INTO hw2.companies VALUES ('2', 'Maddev',     '22222222');
INSERT INTO hw2.companies VALUES ('3', 'Softico',    '32323232');

INSERT INTO hw2.customers VALUES ('1', 'Bevanda nuova',    '51515151');
INSERT INTO hw2.customers VALUES ('2', 'Primo straniero',  '44444444');
INSERT INTO hw2.customers VALUES ('3', 'Migliori ragazzi', '31313131');

INSERT INTO hw2.developers VALUES ('1', 'Alex',   22, 'M', '1', 2500);
INSERT INTO hw2.developers VALUES ('2', 'Victor', 29, 'M', '1', 2600);
INSERT INTO hw2.developers VALUES ('3', 'Anna',   28, 'F', '2', 3100);
INSERT INTO hw2.developers VALUES ('4', 'Helen',  23, 'F', '3', 1800);
INSERT INTO hw2.developers VALUES ('5', 'Taras',  35, 'M', '2', 1800);
INSERT INTO hw2.developers VALUES ('6', 'Anton',  33, 'M', '3', 2800);

INSERT INTO hw2.skills VALUES ('1', 'Java', 'Junior');
INSERT INTO hw2.skills VALUES ('2', 'C++',  'Junior');
INSERT INTO hw2.skills VALUES ('3', 'C#',   'Junior');
INSERT INTO hw2.skills VALUES ('4', 'JS',   'Junior');
INSERT INTO hw2.skills VALUES ('5', 'Java', 'Middle');
INSERT INTO hw2.skills VALUES ('6', 'C++',  'Middle');
INSERT INTO hw2.skills VALUES ('7', 'C#',   'Middle');
INSERT INTO hw2.skills VALUES ('8', 'JS',   'Middle');
INSERT INTO hw2.skills VALUES ('9', 'Java', 'Senior');
INSERT INTO hw2.skills VALUES ('10', 'C++', 'Senior');
INSERT INTO hw2.skills VALUES ('11', 'C#',  'Senior');
INSERT INTO hw2.skills VALUES ('12', 'JS',  'Senior');

INSERT INTO hw2.projects VALUES ('1', 'Domino',       '1', '1', '2020-01-01');
INSERT INTO hw2.projects VALUES ('2', 'Opera 3',      '1', '2', '2020-03-01');
INSERT INTO hw2.projects VALUES ('3', 'Integro',      '2', '1', '2020-12-12');
INSERT INTO hw2.projects VALUES ('4', 'Anagramma',    '2', '2', '2021-03-02');
INSERT INTO hw2.projects VALUES ('5', 'Diadas',       '2', '3', '2021-05-15');
INSERT INTO hw2.projects VALUES ('6', 'DueForte',     '3', '1', '2020-09-01');
INSERT INTO hw2.projects VALUES ('7', 'Thunder',      '3', '2', '2020-11-11');
INSERT INTO hw2.projects VALUES ('8', 'BusinessGame', '3', '3', '2020-06-27');

INSERT INTO hw2.developer_skill (skill_id, developer_id) VALUES ('9', '1');
INSERT INTO hw2.developer_skill (skill_id, developer_id) VALUES ('5', '1');
INSERT INTO hw2.developer_skill (skill_id, developer_id) VALUES ('10', '2');
INSERT INTO hw2.developer_skill (skill_id, developer_id) VALUES ('7', '2');
INSERT INTO hw2.developer_skill (skill_id, developer_id) VALUES ('1', '3');
INSERT INTO hw2.developer_skill (skill_id, developer_id) VALUES ('2', '4');
INSERT INTO hw2.developer_skill (skill_id, developer_id) VALUES ('6', '5');
INSERT INTO hw2.developer_skill (skill_id, developer_id) VALUES ('8', '6');

INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('1', '1');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('2', '1');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('1', '2');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('2', '2');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('3', '3');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('3', '5');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('4', '3');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('5', '3');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('5', '5');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('6', '4');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('7', '4');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('7', '6');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('8', '4');
INSERT INTO hw2.developer_project (project_id, developer_id) VALUES ('8', '6');
