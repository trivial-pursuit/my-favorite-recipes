CREATE TABLE categories
(
	id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modified_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
	version     INTEGER                  NOT NULL DEFAULT 0,

--  VARCHAR without restriction: https://wiki.postgresql.org/wiki/Don%27t_Do_This#Don.27t_use_varchar.28n.29_by_default
	title       VARCHAR                  NOT NULL UNIQUE,
	description VARCHAR                  NULL
);
