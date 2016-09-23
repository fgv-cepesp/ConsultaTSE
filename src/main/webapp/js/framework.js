(function() {
  ConsultaTSE.View = (function() {
    function View(container) {
      this.container = container != null ? container : jQuery('<div></div>');
    }

    View.prototype.findComponent = function(component) {
      return this.container.find("[data-component='" + component + "']");
    };

    View.prototype.findInput = function(inputName) {
      return this.container.find("[name='" + inputName + "']");
    };

    View.prototype.findElement = function(query) {
      return this.container.find(query);
    };

    View.prototype.getData = function(key) {
      return this.container.data(key);
    };

    return View;

  })();

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.AsyncView = (function(superClass) {
    extend(AsyncView, superClass);

    function AsyncView(container, address, data) {
      AsyncView.__super__.constructor.call(this, container);
      this.loadView(address, data);
      this.transitionAnimation = new ConsultaTSE.FadeViewAnimation();
      this.loadingView = new ConsultaTSE.LoadingView();
      this.loadElement(this.loadingView.container);
    }

    AsyncView.prototype.onLoad = function() {};

    AsyncView.prototype.loadView = function(address, data) {
      return $.get(address, data).done((function(_this) {
        return function(html) {
          _this.loadElement(jQuery(html));
          return _this.onLoad();
        };
      })(this));
    };

    AsyncView.prototype.loadElement = function(element) {
      return this.transitionAnimation.animateTransition(this.container, element);
    };

    return AsyncView;

  })(ConsultaTSE.View);

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.LoadingView = (function(superClass) {
    extend(LoadingView, superClass);

    function LoadingView() {
      LoadingView.__super__.constructor.call(this);
      this.container.html(jQuery(this.getViewHtml()));
    }

    LoadingView.prototype.getViewHtml = function() {
      return "<div class='row'>\n    <div class='col-md-2 col-md-offset-5'>\n        <div style=\"color: #47c6de\" class=\"la-ball-fall\">\n            <div></div>\n            <div></div>\n            <div></div>\n         </div>\n    </div>\n</div>";
    };

    return LoadingView;

  })(ConsultaTSE.View);

}).call(this);

(function() {
  ConsultaTSE.FadeViewAnimation = (function() {
    function FadeViewAnimation(delay) {
      this.delay = delay != null ? delay : 1300;
    }

    FadeViewAnimation.prototype.animateTransition = function(from, to) {
      return from.addClass('fadeOut animated').delay(this.delay).html(to).removeClass('fadeOut').addClass('fadeIn animated');
    };

    return FadeViewAnimation;

  })();

}).call(this);

(function() {
  ConsultaTSE.SlideViewAnimation = (function() {
    function SlideViewAnimation() {}

    SlideViewAnimation.prototype.animateTransition = function(from, to) {
      var holder;
      holder = from.parent();
      holder.css({
        overflow: 'hidden'
      });
      from.addClass('slideOutLeft animated');
      return setTimeout((function() {
        from.remove();
        return holder.append(to.addClass('animated slideInRight'));
      }), 1300);
    };

    return SlideViewAnimation;

  })();

}).call(this);

//# sourceMappingURL=framework.js.map
