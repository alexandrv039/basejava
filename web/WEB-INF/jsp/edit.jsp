<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>

</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form action="#">
        <input type="hidden" id="uuid" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" id="fullName" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>

        <label title="Контакты" for="contacts"></label>
        <ul id="contacts">
            <c:forEach var="type" items="<%=ContactType.values()%>">

                <li title="${type.title}">
                        ${type.name()}
                    <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                </li>

            </c:forEach>
        </ul>


        <h3>Секции:</h3>

        <ul id="sections">

            <li title="PERSONAL">
                ${SectionType.PERSONAL.title}
                <textarea id="PERSONAL" style="width: 300px; height: 100px;">
                    ${resume.getSection(SectionType.PERSONAL)}
                </textarea>
            </li>

            <li title="OBJECTIVE">
                ${SectionType.OBJECTIVE.title}
                <textarea id="OBJECTIVE" style="width: 300px; height: 100px;">
                    ${resume.getSection(SectionType.OBJECTIVE)}
                </textarea>
            </li>

            <jsp:useBean id="listSection" class="com.urise.webapp.model.ListSection"/>

            <li title="ACHIEVEMENT">
                ${SectionType.ACHIEVEMENT.title}
                <c:set var="listSection" value="${resume.getSection(SectionType.ACHIEVEMENT)}"/>
                <button type="button" name="add" value="Добавить строку"
                        onclick="addInput(document.getElementById('ACHIEVEMENT'))">Добавить строку</button>
                <button type="button" name="del" value="Удалить строку"
                        onclick="delInput(document.getElementById('ACHIEVEMENT'))">Удалить строку</button>


                <ul id="ACHIEVEMENT" name="ACHIEVEMENT">
                    <c:forEach var="sectionList" items="${listSection.list}">
                        <li><input value="${sectionList}"/></li>
                    </c:forEach>
                </ul>
            </li>

            <li title="QUALIFICATIONS">
                ${SectionType.QUALIFICATIONS.title}
                <c:set var="listSection" value="${resume.getSection(SectionType.QUALIFICATIONS)}"/>

                <button type="button" name="add" value="Добавить строку"
                        onclick="addInput(document.getElementById('QUALIFICATIONS'))">Добавить строку
                </button>
                <button type="button" name="del" value="Удалить строку"
                        onclick="delInput(document.getElementById('QUALIFICATIONS'))">Удалить строку
                </button>

                <ul id="QUALIFICATIONS" name="QUALIFICATIONS">
                    <c:forEach var="sectionList" items="${listSection.list}">
                        <li><input value="${sectionList}"/></li>
                    </c:forEach>
                </ul>
            </li>

        </ul>


        <hr>
        <button type="button" onclick="submitResume()">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>

<script>
    function getResume() {
        let resume = {
            uuid: document.getElementById("uuid").value,
            fullName: document.getElementById("fullName").value,
            contacts : getContacts(),
            sections: getSections()
        }
        return resume;
    }

    function getContacts() {
        let contacts = document.getElementById("contacts").children;
        let result = new Object();

        for (let x = 0; x < (contacts.length); x++) {
            let type = contacts[x].children[0].name;
            result[type] = contacts[x].children[0].value;
        }
        return result;
    }

    function getSections() {

        let result = new Object();
        let sections = document.getElementById("sections").children;

        for (let x = 0; x < sections.length; x++) {
            let section = sections[x].children[0];
            let sectionName = section.id;
            let content = new Object();

            if (sectionName === 'PERSONAL' || sectionName === 'OBJECTIVE') {
                content['CLASSNAME'] = "com.urise.webapp.model.TextSection";
                let textObject = new Object();
                textObject['text'] = section.value;
                content['INSTANCE'] = textObject;
                result[sectionName] = content
            } else if (sectionName === 'ACHIEVEMENT' || sectionName === 'QUALIFICATIONS') {
                content['CLASSNAME'] = "com.urise.webapp.model.ListSection";
                let listObject = new Object();
                let list = new Array();
                let listSection = section.children;
                for (i = 0; i < listSection.length; i++) {
                    list.push(listSection[i].children[0].value);
                }
                listObject['list'] = list;
                content['INSTANCE'] = listObject;
                result[sectionName] = content;
            }
        }

        return result;

    }

    async function submitResume() {

        let response = await fetch(window.location.href, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(getResume())
        });

        if (response.ok) {
            location.href = response.url;
        }
    }

    function addInput(element) {
        let li = document.createElement('li');
        let input = document.createElement('input');
        li.append(input);
        element.append(li);
    }

    function delInput(element) {
        let listEl = element.children;
        if (listEl.length > 0) {
            let lastElement = listEl[listEl.length - 1];
            lastElement.remove();
        }
    }

</script>

</body>
</html>
