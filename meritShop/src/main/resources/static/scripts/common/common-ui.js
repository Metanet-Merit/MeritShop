/*
** Zabbix
** Copyright (C) 2001-2014 Zabbix SIA
**
** This program is free software; you can redistribute it and/or modify
** it under the terms of the GNU General Public License as published by
** the Free Software Foundation; either version 2 of the License, or
** (at your option) any later version.
**
** This program is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
** GNU General Public License for more details.
**
** You should have received a copy of the GNU General Public License
** along with this program; if not, write to the Free Software
** Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
**/

$(function(){
	$(".lnb").css("height", $(document).height()-243); // lnb 메뉴 높이
});

$(function() {

	// Store Variables
	var accordion_tab = $('.lnb a.tab'),
		accordion_content = $('.lnb div.content');

	// When the tab is clicked function
	accordion_tab.on('click', function(e){

		// Disable tab links
		e.preventDefault();

		// Remove the active class
		accordion_tab.removeClass('active');

		// Hide the content
		accordion_content.slideUp('normal');

		// If tab is hidden open it and add the active class
		if($(this).next().is(':hidden') == true) {

			$(this).addClass('active');

			$(this).next().slideDown('normal');
		 } 
		  
	 });

	// Open the first tab on load
	accordion_tab.first().addClass('active').next().slideDown('normal'); // lnb

});


$(function(){
	$("#fromDate, #toDate").datepicker(); //달력
});


$(function(){
	 jQuery(".filter-area .folding-on").click(function(){ //filter folding 처리
		 jQuery(".filter-area .filter-cont .item, .filter-area .btn-area").toggle();
		 jQuery(this).toggleClass("folding-off");
		 return false;
	 });
 });

$(function(){
	var ava = $("#available").text(); // graph bar
	var capa = $("#capacity").text();
	var process = ava/capa * 100;
	$(".disk-process span").css("width",process*4.0);
	
});
