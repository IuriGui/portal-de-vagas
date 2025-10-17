# 🚀 Deploy de Aplicação Java Spring Boot com Maven

Este guia descreve o processo completo para realizar o **deploy de uma aplicação Spring Boot** utilizando o **Maven**.  
Ele cobre desde o build do projeto até a execução em ambiente de produção.

---

## 1. 🧩 Pré-requisitos

Antes de iniciar, verifique se você possui as seguintes dependências instaladas no sistema:

| Ferramenta | Versão recomendada | Comando de verificação |
|-------------|--------------------|------------------------|
| Java (JDK)  | 17 ou superior     | `java -version`        |
| Maven       | 3.8+               | `mvn -v`               |
| Git         | Qualquer           | `git --version`        |

💡 **Dica:** o Maven utiliza o `pom.xml` para gerenciar dependências e o processo de build.

---

## 2. 📥 Clonando o Projeto

Clone o repositório do projeto:

```bash
git clone https://github.com/usuario/projeto-spring.git
cd projeto-spring
