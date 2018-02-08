# Desafio HandsOn!

Este endpoint foi desenvolvido utilizando a linguagem de programação Java, com o uso do framework Spring. Para a persistência de dados, foi utilizado o banco de dados em memória H2, que por sua vez é uma implementação Java.
A forma como os dados foram persistidos se deu através de JDBC, uma vez que o uso de JPA para a criação das queries com maior complexidade não estavam se comportando da maneira esperada no H2.

# EndPoints
    
  - http://localhost:8080/checkList - GET Lista todas as informações relacionadas a estádios, modalidades, etapas e federações.
  - http://localhost:8080/competition?modality= - GET Lista todas as competições cadastradas. O parametro modality é opcional
  - http://localhost:8080/competition - POST Recebe um objeto contendo as chaves necessárias para formar uma partida.
    {
	"fedaration1Id":4,
    "federation2Id":6,
    "stepId":5,
    "stadiumId":2,
    "modalityId":1,
    "startDate":"2017-18-15T16:30:00",
    "finalDate":"2017-18-15T17:00:00"
}
  - Para quaisquer dúvidas com relação à chamada dos EndPoints, o projeto acompanha um arquivo exportado do PostMan, ferramenta utilizada para testes, nomeado como: "HandsOn.postman_collection.json" necessitando apenas fazer sua importação para uso.
# Obrigado.
  
