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

    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, java.lang.String>"/>
    <h3><%=sectionEntry.getKey()%>
    </h3>

    <c:if test="${sectionEntry.key==SectionType.PERSONAL || sectionEntry.key==SectionType.OBJECTIVE}">
        <p>${sectionEntry.value}</p>

    </c:if>

    <c:if test="${sectionEntry.key==SectionType.QUALIFICATIONS || sectionEntry.key==SectionType.ACHIEVEMENT}">
        <c:set var="listSection" value="${sectionEntry.value}"></c:set>
        <jsp:useBean id="listSection"
                     type="com.urise.webapp.model.ListSection"/>
        <ul>
            <c:forEach var="listEntry" items="${listSection.list}">
                <li>${listEntry}</li>
            </c:forEach>
        </ul>

    </c:if>
    <br>
    </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>