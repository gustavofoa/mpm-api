const { series, src, dest, parallel, watch }  = require('gulp'),
        clean                       = require('gulp-clean'),
        nunjucks                    = require('gulp-nunjucks'),
        data                        = require('gulp-data'),
        path                        = require("path"),
        fs                          = require('fs'),
        browserSync = require('browser-sync').create()

function cleanOutput() {
    return src('output/*')
        .pipe(clean({force: true}));
}

function generatePages(){
    src('./app/templates/[^base]*.html')
        .pipe(data(function(file) {
            const jsonPath = './sample-data/' + path.basename(file.path, path.extname(file.path)) + '.json';
            if(fs.existsSync(jsonPath))
                return require(jsonPath);
            return {};
        }))
        .pipe(nunjucks.compile())
        // .pipe(htmlmin({
        //     collapseWhitespace: true
        // }))
        .pipe(dest('output'));
}

function livereload() {
    browserSync.init({
        server: {
            baseDir: "output"
        }
    });
}

function htmlWatch() {

    watch("./app/templates/**").on('change', series(cleanOutput, generatePages));
    watch("./sample-data/**").on('change', series(cleanOutput, generatePages));
    watch("./css/**").on('change', series(cleanOutput, generatePages));
    watch("./js/**").on('change', series(cleanOutput, generatePages));
    watch("./output/**").on('change', browserSync.reload);

}

function copyStatics(){
    src('./assets/**').pipe(dest('output/assets'));
    src('./css/**').pipe(dest('output/css'));
    src('./fonts/**').pipe(dest('output/fonts'));
    src('./js/**').pipe(dest('output/js'));
    src('./locale/**').pipe(dest('output/locale'));
}

exports.default = series(cleanOutput, parallel(livereload, htmlWatch, copyStatics, generatePages));
