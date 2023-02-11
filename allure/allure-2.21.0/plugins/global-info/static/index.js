const template = `
<h2 class="widget__title">{{ title }}
<span class="widget__subtitle">{{items.length}} items total</span>
</h2>
<div class="table table_hover widget__table">
{{#if items}}
    {{#each items}}
    <div class="table__row" disabled>
        <div class="table__col long-line shorter_col">
            {{name}}
        </div>
        {{#if display_value}}
        <div class="table__col long-line">
            <a class="link" href="{{value}}">{{display_value}}</a>
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
        <div class="table__col table__col_center"> Show All </div>
    </a>
    {{/if}}
{{else}}
    <div class="table__row">
        <div class="table__col table__col_center">
            No messages, warnings or gloabl errors
        </div>
    </div>
{{/if}}
</div>

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
            title: 'QA Info & Warnings'
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