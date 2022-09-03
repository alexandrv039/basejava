<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
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
                <textarea id="PERSONAL" style="width: 300px; height: 100px;">${resume.getSection(SectionType.PERSONAL).toString()}</textarea>
            </li>

            <li title="OBJECTIVE">
                ${SectionType.OBJECTIVE.title}
                <textarea id="OBJECTIVE" style="width: 300px; height: 100px;">${resume.getSection(SectionType.OBJECTIVE)}</textarea>
            </li>

            <jsp:useBean id="listSection" class="com.urise.webapp.model.ListSection"/>

            <li title="ACHIEVEMENT">
                ${SectionType.ACHIEVEMENT.title}
                <c:set var="listSection" value="${resume.getSection(SectionType.ACHIEVEMENT)}"/>
                <ul id="ACHIEVEMENT" name="ACHIEVEMENT">
                    <c:forEach var="sectionList" items="${listSection.list}">
                        <li><input value="${sectionList}"/></li>
                    </c:forEach>
                </ul>
                <button type="button" name="add" value="Добавить строку"
                        onclick="addInput(document.getElementById('ACHIEVEMENT'))">Добавить строку</button>
                <button type="button" name="del" value="Удалить строку"
                        onclick="delInput(document.getElementById('ACHIEVEMENT'))">Удалить строку</button>


            </li>

            <li title="QUALIFICATIONS">
                ${SectionType.QUALIFICATIONS.title}
                <c:set var="listSection" value="${resume.getSection(SectionType.QUALIFICATIONS)}"/>
                <ul id="QUALIFICATIONS" name="QUALIFICATIONS">
                    <c:forEach var="sectionList" items="${listSection.list}">
                        <li><input value="${sectionList}"/></li>
                    </c:forEach>
                </ul>

                <button type="button" name="add" value="Добавить строку"
                        onclick="addInput(document.getElementById('QUALIFICATIONS'))">Добавить строку
                </button>
                <button type="button" name="del" value="Удалить строку"
                        onclick="delInput(document.getElementById('QUALIFICATIONS'))">Удалить строку
                </button>

            </li>

            <li title="EXPERIENCE">
                ${SectionType.EXPERIENCE.title}
                <c:set var="organizationsExpirience" value="${resume.getSection(SectionType.EXPERIENCE)}"/>
                <ul id="EXPERIENCE">
                    <c:forEach var="organizationExpirience" items="${organizationsExpirience.organizations}">
                        <jsp:useBean id="organizationExpirience" class="com.urise.webapp.model.Organization"/>
                        <li class="experience-element">
                            <label>
                                Наименование организации:
                                <input title="organization_name" style="display: inline" value="${organizationExpirience.title}">
                            </label>
                            <section title="link" style="display: inline">
                                <label>
                                    url:
                                    <input value="${organizationExpirience.link.url}">
                                </label>
                            </section>
                            <ul title="organization_positions">
                                <c:forEach var="position" items="${organizationExpirience.periods}">
                                    <li>
                                        <label>
                                            Должность:
                                            <input value="${position.position}" style="width: 500px">
                                        </label>
                                        <div>
                                            <span>Период:</span>
                                            <input type="date" value="${position.dateFrom}">
                                            <input type="date" value="${position.dateTo}">
                                        </div>
                                        <span>Описание:</span>
                                        <textarea style="width: 500px; height: 100px">${position.description}</textarea>
                                    </li>
                                </c:forEach>
                            </ul>
                            <button type="button" name="addPosition">Добавить позицию</button>
                            <button type="button" name="deletePosition">Удалить позицию</button>
                            <br>
                        </li>
                    </c:forEach>
                </ul>
                <button type="button" name="add" value="Добавить организацию"
                        onclick="addOrganization(document.getElementById('EXPERIENCE'))">Добавить организацию
                </button>
                <button type="button" name="del" value="Удалить организацию"
                        onclick="delOrganization(document.getElementById('EXPERIENCE'))">Удалить организацию
                </button>
                <br>
            </li>


            <li title="EDUCATION">
                ${SectionType.EDUCATION.title}
                <c:set var="organizationsEducation" value="${resume.getSection(SectionType.EDUCATION)}"/>
                <ul id="EDUCATION">
                    <c:forEach var="organizationEducation" items="${organizationsEducation.organizations}">
                        <jsp:useBean id="organizationEducation" class="com.urise.webapp.model.Organization"/>
                        <li class="education-element">
                            <label>
                                Наименование учебного заведения:
                                <input title="organization_name" style="display: inline" value="${organizationEducation.title}">
                            </label>
                            <section title="link" style="display: inline">
                                <label>
                                    url:
                                    <input value="${organizationEducation.link.url}" alt="url">
                                </label>
                            </section>
                            <ul title="organization_positions">
                                <c:forEach var="position" items="${organizationEducation.periods}">
                                    <li>
                                        <label>
                                            Специальность:
                                            <input value="${position.position}" style="width: 500px">
                                        </label>
                                        <div>
                                            <span>Период:</span>
                                            <input type="date" value="${position.dateFrom}">
                                            <input type="date" value="${position.dateTo}">
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                            <button type="button" name="addPosition">Добавить позицию</button>
                            <button type="button" name="deletePosition">Удалить позицию</button>
                            <br>
                        </li>
                    </c:forEach>
                </ul>

                <button type="button" name="add" value="Добавить организацию"
                        onclick="addOrganization(document.getElementById('EDUCATION'))">Добавить организацию
                </button>
                <button type="button" name="del" value="Удалить организацию"
                        onclick="delOrganization(document.getElementById('EDUCATION'))">Удалить организацию
                </button>

                <br>
            </li>

        </ul>


        <hr>
        <button type="button" onclick="submitResume()">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>

<script>

    let experiencesElements = document.querySelectorAll('.experience-element');
    for (let exp of experiencesElements) {

        exp.children['addPosition'].onclick = function (){
            addPosition(exp, true);
        }
        exp.children['deletePosition'].onclick = function (){
            deletePosition(exp);
        }
    }

    let educationsElements = document.querySelectorAll('.education-element');
    for (let edu of educationsElements) {
        edu.children['addPosition'].onclick = function (){
            addPosition(edu, false);
        }
        edu.children['deletePosition'].onclick = function (){
            deletePosition(edu);
        }
    }


    function addPosition(section, isExperience) {
        let positions = section.children[2];

        let liPos = document.createElement('li');
        let poslabel = document.createElement('label');
        if (isExperience) {
            poslabel.textContent = "Должность:";
        } else poslabel.textContent = "Специальность:";
        let input = document.createElement('input');
        input.style.width = "500px";
        poslabel.append(input);
        liPos.append(poslabel);

        let span = document.createElement('span');
        span.textContent = "Период:";
        let inputFrom = document.createElement('input');
        let inputTo = document.createElement('input');
        inputFrom.type = "date";
        inputTo.type = "date";
        let div = document.createElement('div');
        div.append(span);
        div.append(inputFrom);
        div.append(inputTo);
        liPos.append(div);

        if (isExperience) {
            let spanAbout = document.createElement('span');
            spanAbout.textContent = "Описание:";
            let textArea = document.createElement('textarea');
            textArea.style.width = "500px";
            textArea.style.height = "100px";
            liPos.append(spanAbout);
            liPos.append(textArea);
        }

        positions.append(liPos);
    }

    function deletePosition(section) {
        let positions = section.children[2];
        positions.lastChild.remove();
    }

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
                let text = section.value.trim();
                if (text == '') {
                    continue;
                }
                textObject['text'] = text;
                content['INSTANCE'] = textObject;
                result[sectionName] = content
            } else if (sectionName === 'ACHIEVEMENT' || sectionName === 'QUALIFICATIONS') {
                content['CLASSNAME'] = "com.urise.webapp.model.ListSection";
                let listObject = new Object();
                let list = new Array();
                let listSection = section.children;
                for (i = 0; i < listSection.length; i++) {
                    let text = listSection[i].children[0].value.trim();
                    if (text == '') {
                        continue;
                    }
                    list.push(text);
                }
                listObject['list'] = list;
                content['INSTANCE'] = listObject;
                result[sectionName] = content;
            } else if (sectionName == 'EXPERIENCE' || sectionName == 'EDUCATION') {
                content['CLASSNAME'] = "com.urise.webapp.model.OrganizationSection";
                let organizations = new Array();

                let listExperience = section.children;
                for (let i = 0; i < listExperience.length; i++) {
                    organizationObject = new Object();
                    let orgInfo = listExperience[i].children;
                    organizationObject['title'] = orgInfo[0].children[0].value.trim();
                    let link = new Object();
                    link['name'] = orgInfo[0].children[0].value.trim();
                    link['url'] = orgInfo[1].children[0].children[0].value;
                    organizationObject['link'] = link;
                    let positions = new Array();
                    let list = orgInfo[2].children;
                    for (let n = 0; i < list.length; i++) {
                        let posContent = list[n].children;
                        let position = new Object();
                        position['position'] = posContent[0].children[0].value;
                        position['dateFrom'] = new Date(posContent[1].children[1].value).toISOString();
                        position['dateTo'] = new Date(posContent[1].children[2].value).toISOString();
                        if (sectionName == 'EXPERIENCE') {
                            position['description'] = posContent[3].value;
                        }
                        positions.push(position);
                    }
                    organizationObject['positions'] = positions;
                    organizations.push(organizationObject);
                }
                organizationsObj = new Object();
                organizationsObj['organizations'] = organizations;
                content['INSTANCE'] = organizationsObj;
                result[sectionName] = content;

            }
        }

        return result;

    }

    async function submitResume() {

        if (!validate()) {
            return;
        }

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

    function addOrganization(element) {

        let li = document.createElement('li');

        let inputName = document.createElement('input');
        inputName.title = "organization_name";

        let nameOrg = document.createElement('label');
        if (element.id === 'EXPERIENCE') {
            nameOrg.textContent = "Наименование организации:";
        } else nameOrg.textContent = "Наименование учебного заведения:";
        nameOrg.append(inputName);
        li.append(nameOrg);

        let inputUrl = document.createElement('input');
        let labelUrl = document.createElement('label');
        labelUrl.textContent = "url:";
        labelUrl.append(inputUrl);
        let sectionUrl = document.createElement('section');
        sectionUrl.title = "url";
        sectionUrl.style.display = "inline";
        sectionUrl.append(labelUrl);
        li.append(sectionUrl);

        let liPos = document.createElement('li');
        let poslabel = document.createElement('label');
        if (element.id === 'EXPERIENCE') {
            poslabel.textContent = "Должность:";
        } else poslabel.textContent = "Специальность:";
        let input = document.createElement('input');
        input.style.width = "500px";
        poslabel.append(input);
        liPos.append(poslabel);

        let span = document.createElement('span');
        span.textContent = "Период:";
        let inputFrom = document.createElement('input');
        let inputTo = document.createElement('input');
        inputFrom.type = "date";
        inputTo.type = "date";
        let div = document.createElement('div');
        div.append(span);
        div.append(inputFrom);
        div.append(inputTo);
        liPos.append(div);

        if (element.id === 'EXPERIENCE') {
            let spanAbout = document.createElement('span');
            spanAbout.textContent = "Описание:";
            let textArea = document.createElement('textarea');
            textArea.style = "width: 500px; height: 100px";
            liPos.append(spanAbout);
            liPos.append(textArea);
        }


        let ul = document.createElement('ul');
        ul.title = "organization_positions";
        ul.append(liPos);

        let buttonAddPosition = document.createElement("button");
        buttonAddPosition.name = "addPosition";
        buttonAddPosition.type = "button";
        buttonAddPosition.innerText = "Добавить позицию";

        let buttonDeletePosition = document.createElement("button");
        buttonDeletePosition.name = "addPosition";
        buttonDeletePosition.type = "button";
        buttonDeletePosition.innerText = "Удалить позицию";
        li.append(ul);
        li.append(buttonAddPosition);
        li.append(buttonDeletePosition);
        li.append(document.createElement("br"));

        element.append(li);
    }

    function delOrganization(element) {
        let listEl = element.children;
        if (listEl.length > 0) {
            let lastElement = listEl[listEl.length - 1];
            lastElement.remove();
        }
    }

    function validate() {

        let result = true;

        let fullName = document.getElementById('fullName').value;
        if (fullName.trim() === '') {
            alert("Имя не может быть пустым");
            result = false;
        }

        let listExperience = document.getElementById('EXPERIENCE').children;
        for (let i = 0; i < listExperience.length; i++) {
            if (listExperience[i].children[0].children[0].value.trim() === '') {
                alert("Наименование организации не может быть пустым");
                result = false;
            }
        }

        let listEducation = document.getElementById('EDUCATION').children;
        for (let i = 0; i < listEducation.length; i++) {
            if (listEducation[i].children[0].children[0].value.trim() === '') {
                alert("Наименование организации не может быть пустым");
                result = false;
            }
        }

        return result;

    }

</script>

</body>
</html>
