ALTER TABLE student
    ADD CONSTRAINT ages_constraint CHECK (age >= 16),
    ALTER COLUMN name SET NOT NULL,
    ADD CONSTRAINT constraint_names UNIQUE (name),
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT name_color_constraint UNIQUE (name, color);