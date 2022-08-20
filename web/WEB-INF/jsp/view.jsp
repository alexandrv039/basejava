<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>

    <h2>Секции:</h2>

    <ul id="sections">

        <li title="PERSONAL">
                <h3>${SectionType.PERSONAL.title}</h3>
            <p id="PERSONAL" style="width: 300px; height: 100px;">
                    ${resume.getSection(SectionType.PERSONAL)}
            </p>
            <br>
        </li>

        <li title="OBJECTIVE">
            <h3>${SectionType.OBJECTIVE.title}</h3>
            <p id="OBJECTIVE" style="width: 300px; height: 100px;">
                    ${resume.getSection(SectionType.OBJECTIVE)}
            </p>
            <br>
        </li>

        <jsp:useBean id="listSection" class="com.urise.webapp.model.ListSection"/>

        <li title="ACHIEVEMENT">
            <h3>${SectionType.ACHIEVEMENT.title}</h3>
            <c:set var="listSection" value="${resume.getSection(SectionType.ACHIEVEMENT)}"/>

            <ul id="ACHIEVEMENT" name="ACHIEVEMENT">
                <c:forEach var="sectionList" items="${listSection.list}">
                    <li>${sectionList}</li>
                </c:forEach>
            </ul>
            <br>
        </li>

        <li title="QUALIFICATIONS">
            <h3>${SectionType.QUALIFICATIONS.title}</h3>
            <c:set var="listSection" value="${resume.getSection(SectionType.QUALIFICATIONS)}"/>


            <ul id="QUALIFICATIONS" name="QUALIFICATIONS">
                <c:forEach var="sectionList" items="${listSection.list}">
                    <li>${sectionList}</li>
                </c:forEach>
            </ul>
            <br>
        </li>

        <li title="EXPERIENCE">
            <h3>${SectionType.EXPERIENCE.title}</h3>
            <c:set var="organizationsExpirience" value="${resume.getSection(SectionType.EXPERIENCE)}"/>
            <ul id="EXPERIENCE">
                <c:forEach var="organizationExpirience" items="${organizationsExpirience.organizations}">
                    <jsp:useBean id="organizationExpirience" class="com.urise.webapp.model.Organization"/>
                    <li>
                        <h4 title="organization_name">${organizationExpirience.title}</h4>
                        <section title="link">
                            ${organizationExpirience.link.url}
                        </section>
                        <ul title="organization_positions">
                            <c:forEach var="position" items="${organizationExpirience.periods}">
                                <li>
                                    <div>${position.position}</div>
                                    <div>
                                        <span title="date_from">${position.dateFrom}</span>
                                        -
                                        <span title="date_to">${position.dateTo}</span></div>
                                    <div>${position.description}</div>
                                </li>
                            </c:forEach>
                        </ul>
                        <br>
                    </li>
                </c:forEach>
            </ul>
            <br>
        </li>


        <li title="EDUCATION">
            <h3>${SectionType.EDUCATION.title}</h3>
            <c:set var="organizationsEducation" value="${resume.getSection(SectionType.EDUCATION)}"/>
            <ul id="EDUCATION">
                <c:forEach var="organizationEducation" items="${organizationsEducation.organizations}">
                    <jsp:useBean id="organizationEducation" class="com.urise.webapp.model.Organization"/>
                    <li>
                        <h4 title="organization_name">${organizationEducation.title}</h4>
                        <section title="link">
                            ${organizationEducation.link.url}
                        </section>
                        <ul title="organization_positions">
                            <c:forEach var="position" items="${organizationEducation.periods}">
                                <li>
                                    <div>${position.position}</div>
                                    <div>
                                        <span title="date_from">${position.dateFrom}</span>
                                        -
                                        <span title="date_to">${position.dateTo}</span>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                        <br>
                    </li>
                </c:forEach>
            </ul>
            <br>
        </li>
    </ul>

    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>