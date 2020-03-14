DELETE FROM e_books;
DELETE FROM industry_report;
DELETE FROM specifications;

INSERT INTO e_books (book_name,description,text,created_by) VALUES
 ('Book-name-0','Book-description-0','Book-text-0','script'),
 ('Book-name-1','Book-description-1','Book-text-1','script'),
 ('Book-name-2','Book-description-2','Book-text-2','script'),
 ('Book-name-3','Book-description-3','Book-text-3','script');
INSERT INTO industry_report (industry_name,description,text,created_by) VALUES
 ('Industry-name-0','Industry-description-0','Industry-text-0','script'),
 ('Industry-name-1','Industry-description-1','Industry-text-1','script'),
 ('Industry-name-2','Industry-description-2','Industry-text-2','script'),
 ('Industry-name-3','Industry-description-3','Industry-text-3','script');
INSERT INTO specifications (specification_name,description,text,created_by) VALUES
 ('Specification-name-0','Specification-description-0','Specification-text-0','script'),
 ('Specification-name-1','Specification-description-1','Specification-text-1','script'),
 ('Specification-name-2','Specification-description-2','Specification-text-2','script'),
 ('Specification-name-3','Specification-description-3','Specification-text-3','script');




