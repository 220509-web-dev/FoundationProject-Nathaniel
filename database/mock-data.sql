SET search_path TO foundations_project;

INSERT INTO app_users (first_name, last_name, username, password)
    VALUES
        ('Mark', 'Green', 'MGreen', 'DumbPass'),
        ('Mary', 'Gray', 'MGray', 'ReallyDumbPass'),
        ('Mack', 'George', 'MGeorge', 'VeryReallyDumbPass'),
        ('Test', 'McTesterson', 'McTest', 'WorstPassYet');