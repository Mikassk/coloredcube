# Projeto base da aula 2

Este projeto contém o código base, vazio, da aula 2. Trata-se dos arquivos necessários para acompanhar a aula em sala.

Nele você encontrará basicamente o mesmo conteúdo da aula passada (do triângulo), porém, com algumas modificações:

# Classe Shader

Foi criada uma classe chamada `Shader` dentro do pacote br.pucpr.mage. Todas as funções de shaders da aula passada 
foram movidas para lá e tornadas estáticas. 

Nesta classe também foi adicionada uma forma para leitura do shader de um arquivo. Isso permitirá que criemos arquivos 
com extensão .vert, .frag e .geom para os shaders, ao invés de usar Strings diretamente colocadas no código.

# Shaders

No pacote `br.pucpr.resource` foram criados dois arquivos chamados `basic.vert` e `basic.frag` contendo os códigos
do fragment e do vertex shader. Repare que os códigos são exatamente os mesmos usados nas variáveis VERTEX_SHADER_CODE 
e FRAGMENT_SHADER_CODE da [aula passada](https://github.com/progjogos3d/opengl01-02).

Na classe, os comentários do código também foram passados para o padrão [javadoc](http://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html). 
Este formato permite gerar documentação online dos comentários, além de ser oficialmente usado na comunidade java. 