class GlobalInfoWidget extends Backbone.Marionette.CollectionView {

    template(data) {
        return widgetTemplate(data)
    }

    serializeData() {
        return {
            items: this.model.get('items'),
        }
    }
}

allure.api.addWidget('widgets', 'global-info', GlobalInfoWidget);