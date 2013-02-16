;(function($) {
	_.templateSettings = { interpolate : /\{\{(.+?)\}\}/g }; // double curly brackets
	
	$(function() {
		var Account = Backbone.Model.extend({
			defaults: {
				name: ''
			}
		});
		
		var AccountCollection = Backbone.Collection.extend({
			url: 'account',
			model: Account
		});
		
		var AccountView = Backbone.View.extend({
			tagName: 'li',
			template: _.template($("#account-template").html()),
			initialize: function() {
				this.model.on("change", this.render, this);
			},
			render: function() {
				this.$el.html(this.template(this.model.toJSON()));
				return this;
			}
		});
		
		var AccountCollectionView = Backbone.View.extend({
			initialize: function() {
			    this.collection.on('add', this.addOne, this);
			    this.collection.on('reset', this.addAll, this);
			    this.collection.on('all', this.render, this);
				this.collection.fetch();
			},
			render: function() {
				this.$el.empty();
				this.addAll();
				return this;
			},
			addOne: function(item) {
				var view = new AccountView({model: item});
				this.$el.append(view.render().el);
			},
			addAll: function() {
				this.collection.each(this.addOne, this);
			}
		});
		
		var accounts = new AccountCollection();
		
		window.accounting = {
			Account: Account,
			AccountCollection: AccountCollection,
			AccountView: AccountView,
			AccountCollectionView: AccountCollectionView,
			accounts: accounts
		};
		
		new accounting.AccountCollectionView({
			el: '#accounts',
			collection: window.accounting.accounts
		});
	});
})(jQuery);