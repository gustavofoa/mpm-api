  $(document).ready(function() {
    $("<div class='btn-show'><i class='fa fa-code mr-8'></i>show code</div>").insertAfter(".row-code");
    $("<pre class='pre-code prettyprint'></pre>").insertAfter(".btn-show");
    $(".pre-code").hide();
    $(".btn-show").each(function() {
      var cloned = $(this).prev('.row-code').find("div[class^='col-']").first().clone();
      $(cloned).find("p").text("...");
      $(cloned).find("img").attr("src", "#");
      code = $(cloned).html().split("\n").filter(function(n) {
          return (n.replace(/\s+$/, '') != '');
        }),
        spacesOnLeft = code[0].match(/^ */)[0].length;
      for (var i = 0, len = code.length; i < len; i++) {
        var $output = $(this).next(),
          existing_text = $output.text(),
          new_text = code[i].substring(spacesOnLeft);
        $output.text(existing_text + new_text + '\n');
      }
      $(this).next().removeClass("prettyprinted");
      prettyPrint();
    });
    $(".btn-show").click(function() {
      $(this).next().slideToggle("fast", function() {});
    });
  });