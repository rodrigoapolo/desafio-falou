# DictionMaster - Documentação do Projeto

Este projeto consiste na implementação do aplicativo DictionMaster, um dicionário de inglês baseado na API gratuita Free Dictionary. O objetivo é avaliar o nível de maturidade do desenvolvedor em relação à organização de código, visão de arquitetura, respeito ao design e experiência do usuário (UX).

## Requisitos do Projeto

### Funcionalidades Principais
1. **Busca no Dicionário:**
   - Realizar busca por termos em inglês na API Free Dictionary.
   - Exibir resultados de pronúncia, significados e exemplos.

2. **Áudio do Termo:**
   - Permitir a reprodução do áudio do termo consultado.

3. **Limite de Buscas Gratuitas:**
   - Limitar cada usuário a 10 buscas gratuitas por dia.
   - Apresentar uma tela de compra quando o limite é ultrapassado.

### Integração com a API
- Usar a API Free Dictionary para suportar a busca.
- Utilizar bibliotecas como Retrofit para fazer requisições HTTP.

### Cache de Busca
- Implementar um mecanismo de cache para evitar requisições repetidas para buscas iguais.

### Telas e Design
- As telas estão disponíveis no Adobe Xd [link telas](https://xd.adobe.com/view/d947dd94-0bdc-4d90-b4f0-dccf6bcaa1ca-8c14/grid/).
- O design inclui informações sobre fontes, cores (RGB), e ícones necessários para suportar diferentes dispositivos Android.

## Implementação

### Estrutura do Projeto
- Organizar o código de maneira clara e coesa.
- Seguir boas práticas de arquitetura e design de software.

### Integração com a API
- Utilizar Retrofit ou outra biblioteca para realizar requisições HTTP.
- Formatando corretamente os resultados da API em termos de modelos de dados.

### Cache de Busca
- Implementar um mecanismo de cache eficiente para evitar requisições redundantes.

### Controle de Limite de Buscas Gratuitas
- Controlar e exibir a quantidade de buscas realizadas por cada usuário.
- Apresentar uma tela de compra quando o limite gratuito for excedido.

### Experiência do Usuário (UX)
- Respeitar o design proposto nas telas do Adobe Xd.
- Garantir uma boa experiência de usuário durante a navegação e interação com o aplicativo.
