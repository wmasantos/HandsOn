CREATE TABLE IF NOT EXISTS MODALIDADE(
  ID INTEGER PRIMARY KEY AUTO_INCREMENT,
  NOME VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS ESTADIO(
  ID INTEGER PRIMARY KEY AUTO_INCREMENT,
  NOME VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS ETAPA(
  ID INTEGER PRIMARY KEY AUTO_INCREMENT,
  NOME VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS FEDERACAO(
  ID INTEGER PRIMARY KEY AUTO_INCREMENT,
  NOME VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS JOGO(
  ID INTEGER PRIMARY KEY AUTO_INCREMENT,
  IDFEDERACAO1 INTEGER NOT NULL,
  IDFEDERACAO2 INTEGER NOT NULL,
  IDETAPA INTEGER NOT NULL,
  IDESTADIO INTEGER NOT NULL,
  IDMODALIDADE INTEGER NOT NULL,
  DATA_INICIO DATETIME NOT NULL,
  DATA_TERMINO DATETIME NOT NULL,

  CONSTRAINT FK_FED1 FOREIGN KEY(IDFEDERACAO1) REFERENCES FEDERACAO(ID),
  CONSTRAINT FK_FED2 FOREIGN KEY(IDFEDERACAO2) REFERENCES FEDERACAO(ID),
  CONSTRAINT FK_ETA FOREIGN KEY(IDETAPA) REFERENCES ETAPA(ID),
  CONSTRAINT FK_EST FOREIGN KEY(IDESTADIO) REFERENCES ESTADIO(ID),
  CONSTRAINT FK_MOD FOREIGN KEY(IDMODALIDADE) REFERENCES MODALIDADE(ID)
);