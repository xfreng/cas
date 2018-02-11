<!DOCTYPE html>

<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
<head>
  <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <title><spring:message code="cas.index.name"/> &#8211; <spring:message code="cas.index.title"/></title>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>


    <spring:theme code="standard.custom.css.file" var="customCssFile" />
    <link rel="stylesheet" href="<c:url value="${customCssFile}" />" />
    <link rel="icon" href="<c:url value="/favicon.ico" />" type="image/x-icon" />

</head>
<body id="cas">

<div id="container">
  <header>
    <a id="logo" href="<spring:message code="cas.index.url" />" title="<spring:message code="logo.title" />"><spring:message code="cas.index.name"/></a>
    <h1><spring:message code="cas.index.title"/></h1>
  </header>
  <div id="content">
