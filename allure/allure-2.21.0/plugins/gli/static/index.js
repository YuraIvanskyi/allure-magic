const template = `
<h2 class="widget__title">❗{{ title }}
{{#if items}}
<span class="widget__subtitle">{{items.length}} items total</span>
{{/if}}</h2>

{{#if items}}
<div class="table  table_hover widget__table">
    {{#each items}}
    <div class="table__row" disabled>
        <div class="table__col long-line">
            {{name}}
        </div>
        {{#if display_value}}
        <div class="table__col long-line">
            <a href="{{value}}">{{display_value}}</a>
        </div>
        {{else}}
        <div class="table__col long-line">
            {{value}}
        </div>
        {{/if}}
    </div>
    {{/each}}
    {{#if overLimit}}
    <a class="table__row clickable">
        <div class="table__col center">{{ Show All }}</div>
    </a>
    {{/if}}
</div>
{{else}}
<div class="widget__noitems">{{ No messages }}</div>
{{/if}}
`

class GlobalInfoWidget extends Backbone.Marionette.View {
    template(data) {
        return Handlebars.compile(template)(data)
    }
    initialize() {
        this.listLimit = 10;
    }
    serializeData() {
        const items = this.model.get("items");
        return {
            items: items.slice(0, this.listLimit),
            overLimit: items.length > this.listLimit,
            title: 'Warnings & Info'
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