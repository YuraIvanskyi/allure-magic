class GlobalInfoWidget extends Backbone.Marionette.View {

    template(data) {
        return widgetTemplate(data)
    }

    serializeData() {
        return {
            items: this.model.get('items'),
        }
    }
}

allure.api.addWidget('widgets', 'global-info-widget', allure.components.WidgetStatusView.extend({
    title: 'Global Warnings',
    showLinks: true
}));