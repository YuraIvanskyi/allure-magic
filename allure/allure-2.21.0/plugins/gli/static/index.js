const template = `
<h2 class="widget__title">⚠️{{ title }}</h2>
{{#if items}}
<div class="table  table_hover widget__table">
    {{#each items}}
    <div class="table__row" disabled>
        <div class="table__col long-line">
            🌍{{name}}
        </div>
        {{#if display_value}}
        <div class="table__col long-line">
            {{display_value}}
        </div>
        {{else}}
        <div class="table__col long-line">
            {{value}}
        </div>
        {{/if}}
        <div class="table__col long-line">
            {{decoration}}
        </div>
    </div>
    {{/each}}
    {{#if overLimit}}
    <a class="table__row clickable">
        <div class="table__col center">{{ widget.globalInfo.showAll }}</div>
    </a>
    {{/if}}
</div>
{{else}}
<div class="widget__noitems">{{ widget.globalInfo.empty }}</div>
{{/if}}
`

class GlobalInfoWidget extends Backbone.Marionette.View {
    template(data) {
        return Handlebars.compile(template)(data)
    }
    initialize() {
        this.listLimit = 15;
    }
    serializeData() {
        const items = this.model.get("items");
        return {
            items: items.slice(0, this.listLimit),
            overLimit: items.length > this.listLimit,
            title: 'Global Warnings & Info'
        };
    }
}
allure.api.addWidget('widgets', 'global-info', GlobalInfoWidget);
allure.api.addTranslation('en', {
    widget: {
        globalInfo: {
            name: 'Global Info',
            showAll: 'Show All',
            empty: 'Empty'
        }
    }
});