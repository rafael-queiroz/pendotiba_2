var Logic = Logic || {};

Logic.onSidebarToggleRequest = function(event) {
  event.preventDefault();
  $(this).blur();

  $('.js-sidebar, .js-content').toggleClass('is-toggled');
};

Logic.onSearchModalShowRequest = function(event) {
  event.preventDefault();

  $('.js-search-modal').fadeIn('slow');
  $('body').addClass('Logic-no-scroll');
  
  $('.js-search-modal-input').val('').select();
  
};

Logic.onSearchModalCloseRequest = function(event) {
  event.preventDefault();

  $('.js-search-modal').hide();
  $('body').removeClass('Logic-no-scroll');
};

//Logic.onFormLoadingSubmit = function(event) {
  //event.preventDefault();

  //Logic.showLoadingComponent();

  //setTimeout(function() {
  //  Logic.hideLoadingComponent();
  //}, 2000);
//};

Logic.showLoadingComponent = function() {
  $('.js-loading-overlay').css('display', 'table').hide().fadeIn('slow');
};

Logic.hideLoadingComponent = function() {
  $('.js-loading-component').fadeOut('fast');
};

Logic.initStickyTableHeaders = function() {
  if ($(window).width() >= 992) { 
    var stickyRef = $('.js-sticky-reference');
    var stickyTable = $('.js-sticky-table');

    if (stickyRef && stickyTable) {
      stickyTable.stickyTableHeaders({fixedOffset: stickyRef});
    }
  }
};

Logic.onMenuGroupClick = function(event) {
  var subItems = $(this).parent().find('ul');

  if (subItems.length) {
    event.preventDefault();
    $(this).parent().toggleClass('is-expanded');
  }
};

Logic.initMenu = function() {
  $('.js-menu > ul > li > a').bind('click', Logic.onMenuGroupClick);
  $('.Logic-menu__item .is-active').parents('.Logic-menu__item').addClass('is-expanded is-active');
};

$(function() {
  if (Logic.init) {
    Logic.init();
  }

  Logic.initMenu();
  Logic.initStickyTableHeaders();
  
  // Enable Bootstrap tooltip
  $('.js-tooltip').tooltip();
  
  // Bind events
  $('.js-sidebar-toggle').bind('click', Logic.onSidebarToggleRequest);
  $('.js-search-modal-trigger-show').bind('click', Logic.onSearchModalShowRequest);
  $('.js-search-modal-close').bind('click', Logic.onSearchModalCloseRequest);
  //$('.js-form-loading').bind('submit', Logic.onFormLoadingSubmit);
});