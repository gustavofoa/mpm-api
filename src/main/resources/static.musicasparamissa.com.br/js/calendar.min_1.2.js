function formatDate(a){var b=a.getFullYear().toString(),c=(a.getMonth()+1).toString(),d=a.getDate().toString(),e=(d[1]?d:"0"+d[0])+"/"+(c[1]?c:"0"+c[0])+"/"+b;return e}var calendarOptions={language:"pt-BR",todayHighlight:!0,datas:[],beforeShowDay:function(a){a.setHours(a.getHours()+1);var b=formatDate(a);return this.datas[b]?{enabled:!0,tooltip:this.datas[b].title}:{enabled:!1,tooltip:"Não há sugestões de músicas para este dia ainda."}}};$(document).ready(function(){$.get("/datas.json",function(a){calendarOptions.datas=a;var b=function(a){var b=a.date;b.setHours(b.getHours()+1);var c=formatDate(b),d=calendarOptions.datas[c].url;document.location=d};$("#calendar-navbar").datepicker(calendarOptions).on("changeDate",b),$("#calendar").datepicker(calendarOptions).on("changeDate",b),$("#calendar-footer").datepicker(calendarOptions).on("changeDate",b)})});