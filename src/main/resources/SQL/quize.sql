DROP TABLE IF EXISTS questions_to_quizzes;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;

CREATE TABLE quizzes (
  id INTEGER PRIMARY KEY NOT NULL,
  caption VARCHAR (100) NOT NULL UNIQUE,
  description VARCHAR (200) NOT NULL
);

CREATE TABLE questions (
  id INTEGER PRIMARY KEY NOT NULL,
  caption VARCHAR (100) NOT NULL UNIQUE,
  question VARCHAR (200) NOT NULL
);

CREATE TABLE questions_to_quizzes (
  quiz_id INTEGER NOT NULL,
  question_id INTEGER NOT NULL,
  FOREIGN KEY (quiz_id)
      REFERENCES quizzes (id)
      ON DELETE CASCADE,
  FOREIGN KEY (question_id)
      REFERENCES questions (id)
      ON DELETE CASCADE,
  PRIMARY KEY (quiz_id, question_id)
);

CREATE TABLE answers (
  id INTEGER PRIMARY KEY NOT NULL,
  answer VARCHAR (100) NOT NULL,
  question_id INT NOT NULL,
  FOREIGN KEY (question_id)
      REFERENCES questions (id)
      ON DELETE CASCADE
);

-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------

INSERT INTO quizzes (id, caption, description) VALUES
            (1, 'patterns.def_by_name.easy', 'desc-1');
INSERT INTO questions (id, caption, question) VALUES
            (1, 'First', 'A or B or C?'),
            (2, 'Second', 'X or Y or Z?'),
            (3, 'Third', 'M or N or P?');
INSERT INTO questions_to_quizzes (question_id, quiz_id) VALUES
            (1,1), (2,1), (3,1);
INSERT INTO answers (id, question_id, answer) VALUES
            (1, 1, 'A'), (2, 1, 'B'), (3, 1, 'C'),
            (4, 2, 'X'), (5, 2, 'Y'), (6, 2, 'Z'),
            (7, 3, 'M'), (8, 3, 'N'), (9, 3, 'P');


INSERT INTO quizzes (id, caption, description) VALUES
            (2, 'patterns.name_by_def.easy', 'desc-2');
INSERT INTO questions (id, caption, question) VALUES
            (11, 'One', '1 or 2 or 3?'),
            (12, 'Two', '10 or 20 or 30?'),
            (13, 'Three', '100 or 200 or 300?');
INSERT INTO questions_to_quizzes (question_id, quiz_id) VALUES
            (11,2), (12,2), (13,2);
INSERT INTO answers (id, question_id, answer) VALUES
            (11, 11, '1'), (12, 11, '2'), (13, 11, '3'),
            (14, 12, '10'), (15, 12, '20'), (16, 12, '30'),
            (17, 13, '100'), (18, 13, '200'), (19, 13, '300');
